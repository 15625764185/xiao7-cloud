package com.xiao7.cloud.boot.rocketMQ.base;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * RocketMQ的事务生产者的抽象基类
 */
@Slf4j
@Data
public abstract class AbstractMQTransactionProducer implements TransactionListener {

    @Autowired
    private TransactionMQProducer transactionProducer;


    public SendResult sendMessageInTransaction(Message msg, Object arg) throws RocketException {
        try {
            SendResult sendResult = transactionProducer.sendMessageInTransaction(msg, arg);
            if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
                log.error("事务消息发送失败，topic : {}, msgObj {}", msg.getTopic(), msg);
                throw new RocketException("事务消息发送失败，topic :" + msg.getTopic() + ", status :" + sendResult.getSendStatus());
            }
            log.info("发送事务消息成功，事务id: {}", msg.getTransactionId());
            return sendResult;
        } catch (Exception e) {
            log.error("事务消息发送失败，topic : {}, msgObj {}", msg.getTopic(), msg);
            throw new RocketException("事务消息发送失败，topic :" + msg.getTopic() + ",e:" + e.getMessage());
        }
    }


}
