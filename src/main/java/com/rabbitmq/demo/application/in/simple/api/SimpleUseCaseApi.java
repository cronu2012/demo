package com.rabbitmq.demo.application.in.simple.api;

public interface SimpleUseCaseApi {

    void send(String message);

    void execute(String message);

}
