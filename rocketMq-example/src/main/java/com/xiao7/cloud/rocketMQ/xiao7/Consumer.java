package com.xiao7.cloud.rocketMQ.xiao7;

import com.xiao7.cloud.boot.rocketMQ.annotation.MQConsumer;
import com.xiao7.cloud.boot.rocketMQ.base.AbstractMQPushConsumer;
import com.xiao7.cloud.boot.rocketMQ.base.MessageConstant;

import java.util.Map;

/**
 * @author ：xiao7
 * @date ：Created in 2020/6/14 23:48
 * @description：消费者
 */
@MQConsumer(consumerGroup = "consumer-test-group", topic = "base")
public class Consumer extends AbstractMQPushConsumer<String> {


    @Override
    public boolean process(String message, Map<String, Object> extMap) {
        System.out.println("id:" + extMap.get(MessageConstant.PROPERTY_EXT_MSG_ID) + "====标签:" + extMap.get(MessageConstant.PROPERTY_TAGS) + "消费了:" + message);

        return true;
    }

}
