package com.javaee.maycon.stockmarket.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.javaee.maycon.stockmarket.domain.Company;
import com.javaee.maycon.stockmarket.domain.Message;
import com.javaee.maycon.stockmarket.domain.Person;
import com.javaee.maycon.stockmarket.domain.Stock;
import com.javaee.maycon.stockmarket.domain.StockIssue;
import com.javaee.maycon.stockmarket.domain.StockPurchase;
import com.javaee.maycon.stockmarket.repositories.CompanyRepository;
import com.javaee.maycon.stockmarket.repositories.PersonRepository;
import com.javaee.maycon.stockmarket.repositories.StockRepository;

@Service
public class StockServiceImpl implements StockService {

  @Autowired
  private StockRepository stockRepository;
  
  @Autowired
  private CompanyRepository companyRepository;
  
  @Autowired
  private PersonRepository personRepository;
  
  @Autowired
  private MessageService messageService;
  
  @Override
  public List<Stock> findAll() {
    return stockRepository.findAll();
  }

  @Override
  public Stock findById(String id) {
    return getStockById(id);
  }

  private Stock getStockById(String id) {
    Optional<Stock> stockOptional = stockRepository.findById(id);

    if (!stockOptional.isPresent()) {
      throw new IllegalArgumentException("Stock not found for ID value: " + id.toString());
    }

    return stockOptional.get();
  }
  
  @Override
  public List<Stock> findByOwnerId(String companyId) {
    return stockRepository.findByOwnerId(companyId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public List<Stock> issue(StockIssue stockIssue) {
    ArrayList<Stock> stocks = new ArrayList<Stock>();

    for (int i = 0; i < stockIssue.getQuantity(); i++) {
      Stock stock = new Stock();
      stock.setInitialValue(stockIssue.getInitialValue());
      stock.setCurrentValue(stockIssue.getInitialValue());
      stock.setIssueDate(new Date());
      stock.setOwnerId(stockIssue.getOwnerId());
      stocks.add(stockRepository.save(stock));
    }

    return stocks;

  }

  @Override
  public void requestPurchase(StockPurchase stockPurchase) {
    Message message = new Message();
    message.setStockPurchase(stockPurchase);
    messageService.sendMessage(message);
  }

  @Override
  public void processPurchase(StockPurchase stockPurchase) {
    Optional<Company> companyBuyer = companyRepository.findById(stockPurchase.getBuyerId());
    Optional<Person> personBuyer = personRepository.findById(stockPurchase.getBuyerId());
    Optional<Company> companySeller = companyRepository.findById(stockPurchase.getSellerId());
    Optional<Person> personSeller = personRepository.findById(stockPurchase.getSellerId());
    
    String buyerId = companyBuyer.isPresent() ? companyBuyer.get().getId() : personBuyer.get().getId();
    String sellerId = companySeller.isPresent() ? companySeller.get().getId() : personSeller.get().getId();
    
    List<Stock> sellerStocks = findByOwnerId(sellerId);
    
    for (int i = 0; i < stockPurchase.getQuantity(); i++) {
      Stock stock = sellerStocks.get(i);
      stock.setOwnerId(buyerId);
      stock.setPurchaseDate(new Date());
      stock.setCurrentValue(stockPurchase.getValue());
      stockRepository.save(stock);
    }

  }

}
