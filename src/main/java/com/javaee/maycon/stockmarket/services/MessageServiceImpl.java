package com.javaee.maycon.stockmarket.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javaee.maycon.stockmarket.config.RabbitMQConfig;
import com.javaee.maycon.stockmarket.domain.Message;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public void sendMessage(Message message) {
    this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_MESSAGES, message);
  }
}
