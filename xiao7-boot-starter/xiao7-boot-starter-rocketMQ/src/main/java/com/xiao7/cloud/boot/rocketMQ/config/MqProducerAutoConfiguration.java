package com.xiao7.cloud.boot.rocketMQ.config;

import com.xiao7.cloud.boot.rocketMQ.annotation.MQProducer;
import com.xiao7.cloud.boot.rocketMQ.annotation.MQTransactionProducer;
import com.xiao7.cloud.boot.rocketMQ.base.AbstractMQTransactionProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 自动装配消息生产者
 *
 * @author xiao7
 */
@Slf4j
@Configuration
@ConditionalOnBean(MqBaseAutoConfiguration.class)
@ConditionalOnMissingBean(DefaultMQProducer.class)
public class MqProducerAutoConfiguration extends MqBaseAutoConfiguration {

    @Autowired
    private static DefaultMQProducer producer;

    @Bean
    public DefaultMQProducer exposeProducer() throws Exception {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(MQProducer.class);
        if (CollectionUtils.isEmpty(beans)) {
            return null;
        }
        if (producer == null) {
            Assert.notNull(rocketProperties.getProducerGroup(), "producer group must be defined");
            Assert.notNull(rocketProperties.getNameServerAddress(), "name server address must be defined");
            // 启用了acl控制 则需要组装RPCHook对象
            if (rocketProperties.getAclEnable()) {
                producer = new DefaultMQProducer(rocketProperties.getProducerGroup(), getAclRPCHook());
            } else {
                producer = new DefaultMQProducer(rocketProperties.getProducerGroup());
            }
            producer.setMaxMessageSize(rocketProperties.getMaxMessageSize());
            producer.setNamesrvAddr(rocketProperties.getNameServerAddress());
            producer.setSendMsgTimeout(rocketProperties.getSendMsgTimeout());
            producer.setSendMessageWithVIPChannel(rocketProperties.getVipChannelEnabled());
            producer.setRetryTimesWhenSendFailed(rocketProperties.getRetryTimesWhenSendFailed());
            producer.setRetryTimesWhenSendAsyncFailed(rocketProperties.getRetryTimesWhenSendAsyncFailed());
            producer.start();
        }
        return producer;
    }

    @PostConstruct
    public void configTransactionProducer() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(MQTransactionProducer.class);
        if (CollectionUtils.isEmpty(beans)) {
            return;
        }
        ExecutorService executorService = new ThreadPoolExecutor(beans.size(), beans.size() * 2, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        Environment environment = applicationContext.getEnvironment();
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object value = entry.getValue();
            try {
                AbstractMQTransactionProducer beanObj = (AbstractMQTransactionProducer) value;
                MQTransactionProducer mqTransactionProducerAnnotation = beanObj.getClass().getAnnotation(MQTransactionProducer.class);

                TransactionMQProducer producer;
                if (rocketProperties.getAclEnable()) { // 启用了acl控制 则需要组装RPCHook对象
                    producer = new TransactionMQProducer(environment.resolvePlaceholders(mqTransactionProducerAnnotation.producerGroup()), getAclRPCHook());
                } else {
                    producer = new TransactionMQProducer(environment.resolvePlaceholders(mqTransactionProducerAnnotation.producerGroup()));
                }
                producer.setNamesrvAddr(rocketProperties.getNameServerAddress());
                producer.setExecutorService(executorService);
                producer.setTransactionListener(beanObj);
                producer.start();
                beanObj.setTransactionProducer(producer);
            } catch (Exception e) {
                log.error("构建事务MQ生产者错误 : ", e);
            }
        }
    }
}
