package com.rabbitmq.demo.adapter.in.event;

import com.rabbitmq.demo.application.in.simple.api.SimpleUseCaseApi;
import com.rabbitmq.demo.application.in.simple.impl.SimpleUseCaseImpl;
import com.rabbitmq.demo.application.in.simple.impl.WorkTaskUseCaseImpl;
import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@AllArgsConstructor
public class MessageListener {


    /**
     *
     * @param message
     */
    @RabbitListener(queues = RabbitConfiguration.QUEUE_SIMPLE)
    public void receiverSimple(String message) {
        log.info("{} simple-received: {} queue:{}", SimpleUseCaseImpl.class.getSimpleName() ,
                message,RabbitConfiguration.QUEUE_SIMPLE);
    }

    /**
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
//            StopWatch wat

            log.info("instance {} [x] Received work-task模式 {}", this.instance, in);

//            doWork(in);
//
//            watch.stop();
//            log.info("instance {} [x] [x] Done in {} s", this.instance, + watch.getTotalTimeSeconds());
        }

        private void doWork(String in) throws InterruptedException {
            for (char ch : in.toCharArray()) {
                if (ch == '.') {
                    Thread.sleep(1000);
                }
            }
        }
    }


}
