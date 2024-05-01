package com.rabbitmq.demo.spring.config;

import com.rabbitmq.demo.adapter.in.event.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * RabbitMQ 設置
 */
@Configuration
public class RabbitConfiguration {

    public static final String QUEUE_SIMPLE = "queue.simple";

    public static final String QUEUE_WORK = "queue.work";

    public static final String QUEUE_TWO = "queue.two";

    public static final String QUEUE_THREE = "queue.three";

    public static final String TOPIC_EXCHANGE = "demo.exchange";

    public static final String ROUTING_KEY_ONE = "routing-key.one";

    public static final String ROUTING_KEY_TWO = "routing-key.two";

    @Bean
    @Qualifier("queueSimple")
    public Queue queueSimple() {
        return new Queue(QUEUE_SIMPLE, true);
    }

    @Bean
    @Qualifier("queueWork")
    public Queue queueWork() {
        return new Queue(QUEUE_WORK, true);
    }

    @Bean
    public AtomicInteger dots() {
        return new AtomicInteger(0);
    }

    @Bean
    public AtomicInteger count() {
        return new AtomicInteger(0);
    }

    @Bean
    public MessageListener.WorkReceiver receiver1() {
        return new MessageListener.WorkReceiver("Consumer-A");
    }

    @Bean
    public MessageListener.WorkReceiver receiver2() {
        return new MessageListener.WorkReceiver("Consumer-B");
    }


//    @Bean
//    TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//
//    @Bean
//    FanoutExchange fanoutExchange(){}{return }
//
//    @Bean
//    public Queue queueOne() {
//        return new Queue(QUEUE_ONE);
//    }
//
//    @Bean
//    public Queue queueTwo() {
//        return new Queue(QUEUE_TWO);
//    }
//
//    @Bean
//    public Queue queueThree(){
//        return new Queue(QUEUE_THREE);
//    }
//
//    @Bean
//    public Binding bindingOne(){
//        return BindingBuilder.bind(queueOne()).to(exchange()).with(ROUTING_KEY_ONE);
//    }
//
//    @Bean
//    public Binding bindingTwo(){
//        return BindingBuilder.bind(queueTwo()).to(exchange()).with(ROUTING_KEY_ONE);
//    }
//
//    @Bean
//    public Binding bindingThree(){
//        return BindingBuilder.bind(queueThree()).to(exchange()).with(ROUTING_KEY_TWO);
//    }

//    @Bean
//    public MessageListenerAdapter listenerAdapter(Receiver receiver){
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }


}
