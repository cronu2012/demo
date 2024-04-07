package com.rabbitmq.demo.adapter.in.event;

import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageListener {

    @RabbitListener(queues = RabbitConfiguration.QUEUE_SIMPLE)
    public void receiverSimple(String message) {
        System.out.println("Simple received: " + message);
    }

}
