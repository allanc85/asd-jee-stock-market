package com.javaee.maycon.stockmarket.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockPurchase {

  private String buyerId;
  private String sellerId;
  private Double value;
  private Integer quantity;
  
}
