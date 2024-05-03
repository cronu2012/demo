package com.rabbitmq.demo.application.in.factory;

import com.rabbitmq.demo.adapter.in.rest.SimpleResponse;

public interface RabbitService {

    void send(String message);

    void receive(String message);
}
