package com.javaee.maycon.stockmarket.domain;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  @Id
  private String id;
  private String name;
  private String email;

}
