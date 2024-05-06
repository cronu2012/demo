package com.rabbitmq.demo.application.in.work;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Second Consumer for Type of WorkTask
 */

@Slf4j
@Service
@AllArgsConstructor
public class SecondConsumer implements WorkTaskConsumer{
    private static final String CLASS = SecondConsumer.class.getName();

    @Override
    public void execute(String message) {
        log.info("{} work-task模式:消費者執行商務邏輯: {}", CLASS, message);
    }
}
