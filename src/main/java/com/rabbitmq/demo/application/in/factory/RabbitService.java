package com.rabbitmq.demo.application.in.factory;

public interface RabbitService {

    void send(String message);

    void receive(String message);
}
