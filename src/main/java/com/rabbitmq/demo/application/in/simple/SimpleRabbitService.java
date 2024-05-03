package com.rabbitmq.demo.application.in.simple;

import com.rabbitmq.demo.adapter.in.rest.SimpleResponse;
import com.rabbitmq.demo.application.in.factory.RabbitService;
import com.rabbitmq.demo.application.in.factory.RabbitType;
import com.rabbitmq.demo.application.out.MessageSender;
import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RabbitType
@Service
@AllArgsConstructor
public class SimpleRabbitService implements RabbitService {

    public static final String RABBIT_TYPE = "simple";

    private final MessageSender messageSender;

    @Override
    public void send(String message) {
        try {
            messageSender.sendSimpleTask(message);
        } catch (Exception e) {
            log.error("{} error: {}", MessageSender.class.getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void receive(String message) {
        log.info("{} simple-received: {} queue:{}", SimpleRabbitService.class.getSimpleName() ,
                message, RabbitConfiguration.QUEUE_SIMPLE);
    }

}
