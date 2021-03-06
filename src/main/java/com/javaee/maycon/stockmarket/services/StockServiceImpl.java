package com.javaee.maycon.stockmarket.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.javaee.maycon.stockmarket.config.MailConfig;
import com.javaee.maycon.stockmarket.domain.Company;
import com.javaee.maycon.stockmarket.domain.Message;
import com.javaee.maycon.stockmarket.domain.Person;
import com.javaee.maycon.stockmarket.domain.Stock;
import com.javaee.maycon.stockmarket.domain.StockIssue;
import com.javaee.maycon.stockmarket.domain.StockPurchase;
import com.javaee.maycon.stockmarket.domain.User;
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
      throw new IllegalArgumentException("Stock not found for ID value: " + id);
    }

    return stockOptional.get();
  }

  @Override
  public List<Stock> findByOwnerIdAndCurrentValue(String companyId, Double currentValue) {
    return stockRepository.findByOwnerIdAndCurrentValue(companyId, currentValue);
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
    User buyer = getUserById(stockPurchase.getBuyerId());
    if (buyer == null) {
      throw new IllegalArgumentException(
          "Buyer not found for ID value: " + stockPurchase.getBuyerId());
    }

    User seller = getUserById(stockPurchase.getSellerId());
    if (seller == null) {
      throw new IllegalArgumentException(
          "Seller not found for ID value: " + stockPurchase.getBuyerId());
    }

    List<Stock> sellerStocks = findByOwnerIdAndCurrentValue(seller.getId(), stockPurchase.getValue());
    if (sellerStocks.size() < stockPurchase.getQuantity()) {
      throw new IllegalArgumentException(
          "Seller does not have enough stock to complete the transaction. Please check the quantity and value parameters.");
    }

    Message message = new Message();
    message.setStockPurchase(stockPurchase);
    messageService.sendMessage(message);
  }

  @Override
  public void processPurchase(StockPurchase stockPurchase) {
    User buyer = getUserById(stockPurchase.getBuyerId());
    User seller = getUserById(stockPurchase.getSellerId());

    List<Stock> sellerStocks = findByOwnerIdAndCurrentValue(seller.getId(), stockPurchase.getValue());
    
    for (int i = 0; i < stockPurchase.getQuantity(); i++) {
      Stock stock = sellerStocks.get(i);
      stock.setOwnerId(buyer.getId());
      stock.setPurchaseDate(new Date());
      stock.setCurrentValue(stockPurchase.getValue());
      stockRepository.save(stock);
    }

    MailConfig config = new MailConfig();
    config.sendMail(buyer.getEmail(), "Compra de ações efetivada com sucesso!", String.format(
        "Sua compra de %d ação(es) no valor de R$ %.2f do vendedor \"%s\" foi efetivada com sucesso.",
        stockPurchase.getQuantity(), stockPurchase.getValue(), seller.getName()));
    config.sendMail(seller.getEmail(), "Venda de ações efetivada com sucesso!",
        String.format("%d ação(es) no valor de R$ %.2f foram compradas por \"%s\".",
            stockPurchase.getQuantity(), stockPurchase.getValue(), buyer.getName()));
  }

  private User getUserById(String id) {
    Optional<Company> companyUser = companyRepository.findById(id);
    Optional<Person> personUser = personRepository.findById(id);

    if (companyUser.isPresent())
      return (User) companyUser.get();
    else if (personUser.isPresent())
      return (User) personUser.get();
    else
      return null;
  }

}
