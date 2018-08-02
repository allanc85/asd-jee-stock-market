package com.javaee.maycon.stockmarket.services;

import java.util.List;
import com.javaee.maycon.stockmarket.domain.Stock;
import com.javaee.maycon.stockmarket.domain.StockIssue;
import com.javaee.maycon.stockmarket.domain.StockPurchase;

public interface StockService {

  List<Stock> findAll();

  Stock findById(String id);
  
  List<Stock> findByOwnerId(String companyId);

  List<Stock> issue(StockIssue stockIssue);

  void requestPurchase(StockPurchase stockPurchase);

  void processPurchase(StockPurchase stockPurchase);
}
