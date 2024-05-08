package com.rabbitmq.demo.application.in.crud;

import com.rabbitmq.demo.adapter.in.rest.delete.RabbitObject;
import com.rabbitmq.demo.spring.config.RabbitConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CrudRabbitService {

    private static final String CLASS = CrudRabbitService.class.getName();

    private static final String NAME = "[RabbitMQ]";


    public void delete(RabbitObject request) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        AmqpAdmin amqpAdmin = context.getBean(AmqpAdmin.class);

        List<String> exchanges = request.getExchange();
        log.info("{} 取出交換機 數量:{} 結果:{}",CLASS,exchanges.size(),exchanges);
        List<String> queues = request.getQueue();
        log.info("{} 取出隊列 數量:{} 結果:{}",CLASS,queues.size(),queues);
        if (!exchanges.isEmpty()) {
            for (String exchangeName : exchanges) {
                // 清除交換機
                amqpAdmin.deleteExchange(exchangeName.trim());
            }
        }
        if (!queues.isEmpty()) {
            for (String queueName : queues) {
                // 清除隊列
                amqpAdmin.deleteQueue(queueName.trim());
            }
        }
    }
}
