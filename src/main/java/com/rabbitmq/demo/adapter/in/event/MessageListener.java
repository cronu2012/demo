package com.rabbitmq.demo.adapter.in.event;

import com.rabbitmq.demo.application.in.simple.api.SimpleUseCaseApi;
import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@AllArgsConstructor
public class MessageListener {

    private final SimpleUseCaseApi simpleUseCase;


    @RabbitListener(queues = RabbitConfiguration.QUEUE_SIMPLE)
    public void receiverSimple(String message) {
        simpleUseCase.execute(message);
    }

    @RabbitListener(queues = RabbitConfiguration.QUEUE_WORK)
    public static class WorkReceiver{
        private final int instance;

        public WorkReceiver(int instance) {
            this.instance = instance;
        }

        @RabbitHandler
        public void receiverWork(String in) throws InterruptedException {
            StopWatch watch = new StopWatch();
            watch.start();
            System.out.println("instance " + this.instance +
                    " [x] Received '" + in + "'");
            doWork(in);
            watch.stop();
            System.out.println("instance " + this.instance +
                    " [x] Done in " + watch.getTotalTimeSeconds() + "s");
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
