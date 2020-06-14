package com.xiao7.cloud.boot.rocketMQ.annotation;


import com.xiao7.cloud.boot.rocketMQ.base.MessageConstant;
import com.xiao7.cloud.boot.rocketMQ.base.MessageModel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * RocketMQ消费者自动装配注解
 *
 * @author xiao7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQConsumer {
    /**
     * 消费者组
     *
     * @return 消费者组
     */
    String consumerGroup();

    /**
     * 消费主题
     *
     * @return
     */
    String topic();

    /**
     * 广播模式消费： BROADCASTING
     * 集群模式消费： CLUSTERING
     *
     * @return 消息消费模式
     */
    String messageMode() default MessageModel.CLUSTERING;

    /**
     * 使用线程池并发消费: CONCURRENTLY("CONCURRENTLY"),
     * 单线程消费: ORDERLY("ORDERLY");
     *
     * @return 消费模式
     */
    String consumeMode() default MessageConstant.CONSUME_MODE_CONCURRENTLY;

    /**
     * 消费标签， 默认  {"*"} 全部
     *
     * @return 消费标签
     */
    String[] tag() default {"*"};
}
