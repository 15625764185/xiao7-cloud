package com.xiao7.cloud.boot.rocketMQ.config;

import com.xiao7.cloud.boot.rocketMQ.annotation.MQConsumer;
import com.xiao7.cloud.boot.rocketMQ.base.AbstractMQPushConsumer;
import com.xiao7.cloud.boot.rocketMQ.base.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自动装配消息消费者
 *
 * @author lcb
 */
@Slf4j
@Configuration
@ConditionalOnBean(MqBaseAutoConfiguration.class)
public class MqConsumerAutoConfiguration extends MqBaseAutoConfiguration {

    /**
     * 用于验证是否相同消费组订阅不同的topic+tag
     */
    private ConcurrentHashMap<String, String> validConsumerMap;

    @PostConstruct
    public void init() throws Exception {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(MQConsumer.class);
        validConsumerMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            publishConsumer(entry.getKey(), entry.getValue());
        }
        // wait gc
        validConsumerMap = null;
    }

    private void publishConsumer(String beanName, Object bean) throws Exception {
        MQConsumer mqConsumer = applicationContext.findAnnotationOnBean(beanName, MQConsumer.class);
        if (StringUtils.isEmpty(rocketProperties.getNameServerAddress())) {
            throw new RuntimeException("name server address must be defined");
        }
        assert mqConsumer != null;
        Assert.notNull(mqConsumer.consumerGroup(), "consumer's consumerGroup must be defined");
        Assert.notNull(mqConsumer.topic(), "consumer's topic must be defined");
        if (!AbstractMQPushConsumer.class.isAssignableFrom(bean.getClass())) {
            throw new RuntimeException(bean.getClass().getName() + " - consumer未实现Consumer抽象类");
        }
        Environment environment = applicationContext.getEnvironment();

        String consumerGroup = environment.resolvePlaceholders(mqConsumer.consumerGroup());
        String topic = environment.resolvePlaceholders(mqConsumer.topic());
        String tags = "*";
        if (mqConsumer.tag().length == 1) {
            tags = environment.resolvePlaceholders(mqConsumer.tag()[0]);
        } else if (mqConsumer.tag().length > 1) {
            tags = StringUtils.join(mqConsumer.tag(), "||");
        }

        // 检查consumerGroup
        if (StringUtils.isNotEmpty(validConsumerMap.get(consumerGroup))) {
            String exist = validConsumerMap.get(consumerGroup);
            throw new RuntimeException("消费组重复订阅，请新增消费组用于新的topic和tag组合: " + consumerGroup + "已经订阅了" + exist);
        } else {
            validConsumerMap.put(consumerGroup, topic + "-" + tags);
        }

        // 配置push consumer
        AbstractMQPushConsumer.class.isAssignableFrom(bean.getClass());
        DefaultMQPushConsumer consumer;
        // 启用了acl控制 则需要组装RPCHook对象
        if (rocketProperties.getAclEnable()) {
            consumer = new DefaultMQPushConsumer(consumerGroup, getAclRPCHook(), new AllocateMessageQueueAveragely());
        } else {
            consumer = new DefaultMQPushConsumer(consumerGroup);
        }
        consumer.setNamesrvAddr(rocketProperties.getNameServerAddress());
        consumer.setMessageModel(MessageModel.valueOf(mqConsumer.messageMode()));
        consumer.subscribe(topic, tags);
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.setVipChannelEnabled(rocketProperties.getVipChannelEnabled());
        AbstractMQPushConsumer abstractMQPushConsumer = (AbstractMQPushConsumer) bean;
        //消费顺序选择
        if (MessageConstant.CONSUME_MODE_CONCURRENTLY.equals(mqConsumer.consumeMode())) {
            consumer.registerMessageListener((MessageListenerConcurrently) abstractMQPushConsumer::dealMessage);
        } else if (MessageConstant.CONSUME_MODE_ORDERLY.equals(mqConsumer.consumeMode())) {
            consumer.registerMessageListener((MessageListenerOrderly) abstractMQPushConsumer::dealMessage);
        } else {
            throw new RuntimeException("unknown consume mode ! only support CONCURRENTLY and ORDERLY");
        }
        abstractMQPushConsumer.setConsumer(consumer);

        consumer.start();

        log.info(String.format("%s is ready to subscribe message", bean.getClass().getName()));
    }

}