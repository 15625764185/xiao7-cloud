package com.xiao7.cloud.boot.rocketMQ.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RocketMQ的配置参数
 *
 * @author xiao7
 */
@Getter
@Setter
@ConfigurationProperties(prefix = RocketProperties.PREFIX)
public class RocketProperties {

    public static final String PREFIX = "xiao7.rocketmq";
    /**
     * nameServer 地址
     */
    private String nameServerAddress;
    /**
     * producer group , default to DPG+RANDOM UUID like DPG-fads-3143-123d-1111
     */
    private String producerGroup;
    /**
     * 消息超时时间(毫秒)
     */
    private Integer sendMsgTimeout = 3000;
    /**
     * switch of trace message consumer: send message consumer info to topic: rmq_sys_TRACE_DATA
     */
    private Boolean traceEnabled = Boolean.TRUE;

    /**
     * 启用ip 通道
     */
    private Boolean vipChannelEnabled = Boolean.TRUE;
    /**
     * 发送失败重试次数 默认2
     */
    private int retryTimesWhenSendFailed = 2;
    /**
     * 异步发送失败重试次数 默认2
     */
    private int retryTimesWhenSendAsyncFailed = 2;
    /**
     * 是否启用acl控制
     */
    private Boolean aclEnable = Boolean.FALSE;

    /**
     * Access Key
     */
    private String accessKey;
    /**
     * Secret Key
     */
    private String secretKey;

    /**
     * 消费组
     */
    private String consumerGroup;

    /**
     * Maximum allowed message size in bytes.
     * default 4GB
     */
    private int maxMessageSize = 1024 * 1024 * 4;

}
