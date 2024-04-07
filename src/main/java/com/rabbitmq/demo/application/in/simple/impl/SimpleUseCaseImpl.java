package com.rabbitmq.demo.application.in.simple.impl;

import com.rabbitmq.demo.application.in.simple.api.SimpleUseCaseApi;
import com.rabbitmq.demo.application.out.MessageSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SimpleUseCaseImpl implements SimpleUseCaseApi {

    private static final Logger log = LoggerFactory.getLogger(SimpleUseCaseImpl.class);

    private final MessageSender messageSender;

    @Override
    public void send(String message) {
        try {
            messageSender.sendSimpleTask(message);
        } catch (Exception e) {
            log.error(String.format("%s error: %s", MessageSender.class.getSimpleName(), e.getMessage()));
        }
    }

    @Override
    public void execute(String message) {
        log.info(String.format("%s simple-received: %s",SimpleUseCaseImpl.class.getSimpleName() , message));
    }
}
