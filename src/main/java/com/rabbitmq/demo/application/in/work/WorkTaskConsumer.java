package com.rabbitmq.demo.application.in.work;

/**
 * The Consumer interface of WorkTask Type.
 */
public interface WorkTaskConsumer {

    void execute(String message);
}
