package com.rabbitmq.demo.application.in.fanout;

import com.rabbitmq.demo.application.in.factory.RabbitService;
import com.rabbitmq.demo.application.in.factory.RabbitType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RabbitType
@Service
@AllArgsConstructor
public class FanoutRabbitService implements RabbitService {
    public static final String RABBIT_TYPE = "fanout";

    @Override
    public void send(String message) {

    }

    @Override
    public void receive(String message) {

    }
}
