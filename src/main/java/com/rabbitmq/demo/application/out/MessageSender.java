package com.rabbitmq.demo.application.out;

import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class MessageSender {

    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    AtomicInteger dot;

    AtomicInteger count ;


    public void sendSimpleTask(String message){
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_SIMPLE, message);
        log.info(String.format("%s simple-send: %s",MessageSender.class.getSimpleName() , message));
    }



}
