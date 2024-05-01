package com.rabbitmq.demo.application.in.simple.impl;

import com.rabbitmq.demo.application.in.simple.api.WorkTaskUseCaseApi;
import com.rabbitmq.demo.application.out.MessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;


@Slf4j
@Service
@AllArgsConstructor
public class WorkTaskUseCaseImpl implements WorkTaskUseCaseApi {

    private final MessageSender messageSender;

    @Override
    public void send(String message) {
        try {
            messageSender.sendWorkTask(message);
        } catch (Exception e) {
            log.error("{} error: {}", MessageSender.class.getSimpleName(), e.getMessage());
        }
    }


}
