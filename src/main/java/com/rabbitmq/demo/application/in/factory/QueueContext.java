package com.rabbitmq.demo.application.in.factory;

import com.rabbitmq.demo.adapter.in.rest.SimpleResponse;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class QueueContext implements ApplicationContextAware {

    Map<String, RabbitService> springBeanTypeMap = null;

    private static Map<String, RabbitService> queueServiceMap = null;

    public static final String RABBIT_TYPE = "RABBIT_TYPE";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springBeanTypeMap = applicationContext.getBeansOfType(RabbitService.class);

        if (springBeanTypeMap.isEmpty()) {
            log.error("未設置任何RabbitMQ服務模式");
        }

        List<RabbitService> rabbitServiceList = springBeanTypeMap.entrySet().stream()
                .map(entry -> (RabbitService) getTargetBean(entry.getValue()))
                .toList();

        initQueueService(rabbitServiceList);
    }

    /**
     * 初始化 RabbitMQ 服務模式
     *
     * @param rabbitServiceList
     */
    private void initQueueService(List<RabbitService> rabbitServiceList) {
        queueServiceMap = new ConcurrentHashMap<String, RabbitService>();

        Class<?> clazz = null;
        for (RabbitService rabbitService : rabbitServiceList) {
            try {
                clazz = rabbitService.getClass();
                boolean isExistAnnotation = clazz.isAnnotationPresent(RabbitType.class);
                if (!isExistAnnotation) {
                    continue;
                }
                log.info("初始化 RabbitMQ服務,clazz= {}", clazz);
                Field field = clazz.getDeclaredField(RABBIT_TYPE);
                String rabbitType = (String) field.get(null);
                if (StringUtils.isBlank(rabbitType)) {
                    log.info("獲取 RabbitMQ服務 結果為空, clazz={}", clazz);
                    continue;
                }

                queueServiceMap.put(rabbitType.toLowerCase(), rabbitService);

            } catch (Exception e) {
                log.error("初始化Rabbit服務模式 失敗,clazz= {} ", clazz, e);
            }
        }
        log.info("初始化RabbitMQ服務模式结束,queueServiceMap = {}", queueServiceMap);
    }

    /**
     * 發送RabbitMQ 訊息
     *
     * @param message    發送訊息
     * @param rabbitType 服務模式
     */
    public void send(String message, String rabbitType) {
        RabbitService rabbitService = queueServiceMap.get(rabbitType);
        SimpleResponse response = null;
        if (rabbitService == null) {
            log.error("獲取服務模式為空 所有服務模式為: {}", queueServiceMap);
        } else {
            rabbitService.send(message);
        }
    }

    /**
     * 接收RabbitMQ 訊息
     *
     * @param message    接收訊息
     * @param rabbitType 服務模式
     */
    public void receive(String message, String rabbitType) {
        RabbitService rabbitService = queueServiceMap.get(rabbitType);

        if (rabbitService == null) {
            log.error("獲取服務模式為空 所有服務模式為: {}", queueServiceMap);
        } else {
            rabbitService.receive(message);
        }
    }


    private static <T> T getTargetBean(Object bean) {
        Object object = bean;
        while (AopUtils.isAopProxy(object)) {
            try {
                object = ((Advised) object).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new RuntimeException("get target bean failed", e);
            }
        }
        return (T) object;
    }
}
