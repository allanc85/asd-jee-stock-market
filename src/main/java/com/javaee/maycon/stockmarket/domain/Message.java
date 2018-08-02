package com.javaee.maycon.stockmarket.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private StockPurchase stockPurchase;
}
