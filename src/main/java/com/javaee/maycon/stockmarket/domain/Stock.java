package com.javaee.maycon.stockmarket.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stock {

  @Id
  private String id;
  private String ownerId;
  private Double initialValue;
  private Double currentValue;
  private Date issueDate;
  private Date purchaseDate;
  
}
