package com.rabbitmq.demo.application.in.work;

import com.rabbitmq.demo.application.in.factory.RabbitService;
import com.rabbitmq.demo.application.in.factory.RabbitType;
import com.rabbitmq.demo.application.out.MessageSender;
import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RabbitType
@AllArgsConstructor
public class WorkTaskRabbitService implements RabbitService {

    public static final String RABBIT_TYPE = "worktask";

    private final MessageSender messageSender;

    @Override
    public void send(String message) {
        try {
            messageSender.sendWorkTask(message);
        } catch (Exception e) {
            log.error("{} error: {}", MessageSender.class.getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void receive(String message) {}


}
