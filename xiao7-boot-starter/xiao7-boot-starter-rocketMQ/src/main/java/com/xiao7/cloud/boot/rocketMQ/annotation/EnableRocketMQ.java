package com.xiao7.cloud.boot.rocketMQ.annotation;

import java.lang.annotation.*;

/**
 * 是否启动RocketMQ
 *
 * @author xiao7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableRocketMQ {
}
