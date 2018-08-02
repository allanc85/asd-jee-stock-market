package com.javaee.maycon.stockmarket.controllers.v1;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.javaee.maycon.stockmarket.domain.Stock;
import com.javaee.maycon.stockmarket.domain.StockIssue;
import com.javaee.maycon.stockmarket.domain.StockPurchase;
import com.javaee.maycon.stockmarket.services.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("This is a Stock API")
@RestController
@RequestMapping(StockController.BASE_URL)
public class StockController {

  public static final String BASE_URL = "/api/v1/stocks";

  @Autowired
  private StockService stockService;

  // GET

  @ApiOperation(value = "Get all stocks", produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Stock> findAll() {
    return stockService.findAll();
  }

  @ApiOperation(value = "Get a stock by id", produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public Stock findById(@PathVariable String id) {
    return stockService.findById(id);
  }

  // POST

  @ApiOperation(value = "Issue stocks in the market", produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping({"/issue"})
  @ResponseStatus(HttpStatus.CREATED)
  public List<Stock> issue(@RequestBody StockIssue stockIssue) {
    return stockService.issue(stockIssue);
  }

  @ApiOperation(value = "Purchase stocks from the market",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping({"/purchase"})
  @ResponseStatus(HttpStatus.CREATED)
  public String purchase(@RequestBody StockPurchase stockPurchase) {
    stockService.requestPurchase(stockPurchase);
    return "Solicitação de compra enviada com sucesso.";
  }

}
