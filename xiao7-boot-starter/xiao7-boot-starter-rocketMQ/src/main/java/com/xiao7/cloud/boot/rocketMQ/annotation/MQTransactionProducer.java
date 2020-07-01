package com.xiao7.cloud.boot.rocketMQ.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * RocketMQ事务消息生产者
 *
 * @author xiao7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQTransactionProducer {

    /**
     * *重要* 事务的反查是基于同一个producerGroup为维度
     */
    String producerGroup();
}
