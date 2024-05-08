package com.rabbitmq.demo.application.out;

import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class MessageSender {
    private final RabbitTemplate rabbitTemplate;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendSimpleTask(String message) {
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_SIMPLE, message);
        log.info("{} simple-send: {}", MessageSender.class.getSimpleName(), message);
    }

    public void sendWorkTask(String message) {
        StringBuilder builder = new StringBuilder(message);
        if (dots.incrementAndGet() == 4) {
            dots.set(1);
        }
        for (int i = 0; i < dots.get(); i++) {
            builder.append('.');
        }
        builder.append(count.incrementAndGet());
        String msg = builder.toString();
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_WORK, msg);
        log.info("{} [x] Sent work-task模式: {}", MessageSender.class.getSimpleName(), msg);
    }


}
