package com.rabbitmq.demo.application.in.simple.impl;

import com.rabbitmq.demo.application.in.simple.api.SimpleUseCaseApi;
import com.rabbitmq.demo.application.out.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleUseCaseImpl implements SimpleUseCaseApi {

    private final MessageSender messageSender;

    @Override
    public void send(String message) {

    }

    @Override
    public void execute(String message) {

    }
}
