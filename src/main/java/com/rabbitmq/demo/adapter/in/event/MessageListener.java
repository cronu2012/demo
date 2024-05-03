package com.rabbitmq.demo.adapter.in.event;

import com.rabbitmq.demo.application.in.factory.QueueService;
import com.rabbitmq.demo.application.in.simple.SimpleRabbitService;
import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MessageListener {

    private final QueueService queueService;

    /**
     * 簡單模式Queue 接收
     *
     * @param message
     */
    @RabbitListener(queues = RabbitConfiguration.QUEUE_SIMPLE)
    public void receiverSimple(String message) {
        queueService.queueReceive(message, SimpleRabbitService.RABBIT_TYPE);
    }

    /**
     * WorkTask模式 Queue 接收
     *
     */
    @RabbitListener(queues = RabbitConfiguration.QUEUE_WORK)
    public static class WorkReceiver {
        private final String instance;

        public WorkReceiver(String instance) {
            this.instance = instance;
        }

        @RabbitHandler
        public void receiverWork(String in) throws InterruptedException {
            log.info("instance {} [x] Received work-task模式 {}", this.instance, in);
        }

    }


}
