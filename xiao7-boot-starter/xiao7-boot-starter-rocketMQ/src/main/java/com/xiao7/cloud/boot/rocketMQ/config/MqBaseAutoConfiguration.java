package com.xiao7.cloud.boot.rocketMQ.config;

import com.xiao7.cloud.boot.rocketMQ.annotation.EnableRocketMQ;
import com.xiao7.cloud.boot.rocketMQ.base.AbstractMQProducer;
import com.xiao7.cloud.boot.rocketMQ.base.AbstractMQPushConsumer;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ配置文件
 *
 * @author xiao7
 */
@Configuration
@ConditionalOnBean(annotation = EnableRocketMQ.class)
@AutoConfigureAfter({AbstractMQProducer.class, AbstractMQPushConsumer.class})
@EnableConfigurationProperties(RocketProperties.class)
public class MqBaseAutoConfiguration implements ApplicationContextAware {
    @Autowired
    protected RocketProperties rocketProperties;

    protected ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    public RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials(rocketProperties.getAccessKey(), rocketProperties.getSecretKey()));
    }
}

