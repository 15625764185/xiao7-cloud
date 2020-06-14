package com.xiao7.cloud.boot.rocketMQ.base;

/**
 * RocketMQ的自定义异常
 *
 * @author lcb
 */
public class RocketException extends RuntimeException {
    public RocketException(String msg) {
        super(msg);
    }
}
