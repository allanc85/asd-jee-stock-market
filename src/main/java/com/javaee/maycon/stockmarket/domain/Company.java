package com.javaee.maycon.stockmarket.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Company {

  @Id
  private String id;
  private String name;
  private String email;

}
