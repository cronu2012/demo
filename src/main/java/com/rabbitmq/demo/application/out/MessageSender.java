package com.rabbitmq.demo.application.out;

import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendSimpleTask(String message) throws Exception{
        System.out.println("Simple send: " + message);
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_SIMPLE, message);
    }

}
