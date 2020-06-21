package com.xiao7.cloud.rocketMQ.xiao7;

import com.xiao7.cloud.boot.rocketMQ.annotation.MQProducer;
import com.xiao7.cloud.boot.rocketMQ.base.AbstractMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author ：xiao7
 * @date ：Created in 2020/6/14 23:42
 * @description：生产者
 */
@MQProducer
public class Producer extends AbstractMQProducer {
    @Override
    public void doAfterSyncSend(Message message, SendResult sendResult) {
        System.out.println(message);
        super.doAfterSyncSend(message, sendResult);
    }
}
