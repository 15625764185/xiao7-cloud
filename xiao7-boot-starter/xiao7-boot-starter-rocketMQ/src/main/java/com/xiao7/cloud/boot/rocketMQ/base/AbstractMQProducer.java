package com.xiao7.cloud.boot.rocketMQ.base;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * RocketMQ的生产者的抽象基类
 */
@Slf4j
@Data
public abstract class AbstractMQProducer {
    /**
     * 消息队列选择器
     */
    private static MessageQueueSelector messageQueueSelector = new SelectMessageQueueByHash();

    @Autowired
    private DefaultMQProducer producer;

    /**
     * 同步发送消息
     *
     * @param message 消息体
     * @throws RocketException 消息异常
     */
    public void syncSend(Message message) throws RocketException {
        try {
            SendResult sendResult = producer.send(message);
            log.debug("send rocketMq message ,messageId : {}", sendResult.getMsgId());
            this.doAfterSyncSend(message, sendResult);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new RocketException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }


    /**
     * 同步发送消息,并且顺序消费
     *
     * @param message 消息体
     * @param hashKey 用于hash后选择queue的key
     * @throws RocketException 消息异常
     */
    public void syncSendOrderly(Message message, String hashKey) throws RocketException {
        if (StringUtils.isEmpty(hashKey)) {
            // fall back to normal
            syncSend(message);
        }
        try {
            SendResult sendResult = producer.send(message, messageQueueSelector, hashKey);
            log.debug("send rocketMq message orderly ,messageId : {}", sendResult.getMsgId());
            this.doAfterSyncSend(message, sendResult);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("顺序消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new RocketException("顺序消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }

    /**
     * 重写此方法处理发送后的逻辑
     *
     * @param message    发送消息体
     * @param sendResult 发送结果
     */
    public void doAfterSyncSend(Message message, SendResult sendResult) {
    }

    /**
     * 异步发送消息
     *
     * @param message      msgObj
     * @param sendCallback 回调
     * @throws RocketException 消息异常
     */
    public void asyncSend(Message message, SendCallback sendCallback) throws RocketException {
        try {
            producer.send(message, sendCallback);
            log.debug("send rocketMq message async");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new RocketException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }
}
