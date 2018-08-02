package com.javaee.maycon.stockmarket.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockIssue {

  private String ownerId;
  private Double initialValue;
  private Integer quantity;
  
}
