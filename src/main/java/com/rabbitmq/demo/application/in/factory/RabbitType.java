package com.rabbitmq.demo.application.in.factory;

import java.lang.annotation.*;

/**
 * RabbitMQ 服務模式註解
 * @author loose
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitType {
}
