package com.rabbitmq.demo.spring.config;

import com.rabbitmq.demo.adapter.in.event.MessageListener;
import com.rabbitmq.demo.application.in.simple.SimpleRabbitService;
import com.rabbitmq.demo.application.in.work.WorkTaskRabbitService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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

    public static final String CLASSIC = "queue.";

    public static final String QUEUE_SIMPLE = CLASSIC + SimpleRabbitService.RABBIT_TYPE;

    public static final String QUEUE_WORK = CLASSIC + WorkTaskRabbitService.RABBIT_TYPE;

    public static final String QUEUE_FAN_A = CLASSIC + "fanout.a";

    public static final String QUEUE_FAN_B = CLASSIC + "fanout.b";

    public static final String QUEUE_FAN_C = CLASSIC + "fanout.c";

    public static final String QUEUE_TWO = "queue.two";

    public static final String QUEUE_THREE = "queue.three";

    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    public static final String TOPIC_EXCHANGE = "topic.exchange";

    public static final String ROUTING_KEY_ONE = "routing-key.one";

    public static final String ROUTING_KEY_TWO = "routing-key.two";

    @Bean
    public Queue queueSimple() {
        return new Queue(QUEUE_SIMPLE, true);
    }

    @Bean
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
        return new MessageListener.WorkReceiver(1);
    }

    @Bean
    public MessageListener.WorkReceiver receiver2() {
        return new MessageListener.WorkReceiver(2);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue queueFanA() {
        return new Queue(QUEUE_FAN_A, true);
    }

    @Bean
    public Queue queueFanB() {
        return new Queue(QUEUE_FAN_B, true);
    }

    @Bean
    public Binding binding1(FanoutExchange fanout) {
        return BindingBuilder.bind(queueFanA()).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange fanout) {
        return BindingBuilder.bind(queueFanB()).to(fanout);
    }

//    @Bean
//    TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//

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
