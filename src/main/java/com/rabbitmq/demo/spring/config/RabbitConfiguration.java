package com.rabbitmq.demo.spring.config;

import com.rabbitmq.demo.application.in.simple.SimpleRabbitService;
import com.rabbitmq.demo.application.in.work.WorkTaskRabbitService;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 設置   全部持久化
 *
 * 5/9 改用Rabbit-init部署 棄用Config部署 只留寫法做參考
 */
//@Slf4j
@Configuration
public class RabbitConfiguration {

    public static final String CLASSIC = "queue.";

    public static final String QUEUE_SIMPLE = CLASSIC + SimpleRabbitService.RABBIT_TYPE;

    public static final String QUEUE_WORK = CLASSIC + WorkTaskRabbitService.RABBIT_TYPE;

//    public static final String QUEUE_FAN_A = CLASSIC + "fanout.a";
//
//    public static final String QUEUE_FAN_B = CLASSIC + "fanout.b";
//
//    public static final String FANOUT_EXCHANGE = "fanout.exchange";
//
//    public static final String TOPIC_EXCHANGE = "topic.exchange";
//
//    public static final String ROUTING_KEY_ONE = "routing-key.one";
//
//    public static final String ROUTING_KEY_TWO = "routing-key.two";
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost("localhost");
//        factory.setPort(5672);
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        return factory;
//    }
//
//    /**
//     * 簡單模式Queue
//     */
//    @Bean
//    public Queue queueSimple() {
//        return new Queue(QUEUE_SIMPLE, true);
//    }
//
//    /**
//     * Work-Task模式 Queue
//     */
//    @Bean
//    public Queue queueWork() {
//        return new Queue(QUEUE_WORK, true);
//    }
//
//    /**
//     * Work-Task模式 消費實體1
//     *
//     * @return
//     */
//    @Bean
//    public MessageListener.WorkReceiver receiver1() {
//        return new MessageListener.WorkReceiver(1);
//    }
//
//    /**
//     * Work-Task模式 消費實體2
//     *
//     * @return
//     */
//    @Bean
//    public MessageListener.WorkReceiver receiver2() {
//        return new MessageListener.WorkReceiver(2);
//    }
//
//    /**
//     * 發布訂閱模式 交換機
//     */
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
//    }
//
//    /**
//     * 發布訂閱模式 Queue A
//     *
//     * @return
//     */
//    @Bean
//    public Queue queueFanA() {
//        return new Queue(QUEUE_FAN_A, true);
//    }
//
//    /**
//     * 發布訂閱模式 Queue B
//     *
//     * @return
//     */
//    @Bean
//    public Queue queueFanB() {
//        return new Queue(QUEUE_FAN_B, true);
//    }
//
//    /**
//     * 發布訂閱模式  交換機bind q
//     *
//     * @param fanout FanoutExchange
//     * @return
//     */
//    @Bean
//    public Binding binding1(FanoutExchange fanout, Queue queueFanA) {
//        return BindingBuilder.bind(queueFanA).to(fanout);
//    }
//
//    /**
//     * 發布訂閱模式  交換機bind
//     *
//     * @param fanout FanoutExchange
//     * @return
//     */
//    @Bean
//    public Binding binding2(FanoutExchange fanout, Queue queueFanB) {
//        return BindingBuilder.bind(queueFanB).to(fanout);
//    }
//
//
//    @Bean
//    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }

}
