package com.javaee.maycon.stockmarket.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.javaee.maycon.stockmarket.config.RabbitMQConfig;
import com.javaee.maycon.stockmarket.domain.Message;
import com.javaee.maycon.stockmarket.services.StockService;

@Component
public class MessageListener {

  static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
  
  @Autowired
  private StockService stockService;
  
  @RabbitListener(queues = RabbitMQConfig.QUEUE_MESSAGES)
  public void processMessage(Message message) {
    Gson gson = new Gson();
    logger.info("Message Received: " + gson.toJson(message));
    
    stockService.processPurchase(message.getStockPurchase());
  }
}
