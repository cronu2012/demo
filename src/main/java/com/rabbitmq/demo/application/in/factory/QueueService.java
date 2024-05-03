package com.rabbitmq.demo.application.in.factory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QueueService {

    private final QueueContext queueContext;

    public void queueSend(String message, String rabbitType){

        queueContext.send(message,rabbitType);
    }

    public void queueReceive(String message, String rabbitType){

        queueContext.receive(message,rabbitType);
    }
}
