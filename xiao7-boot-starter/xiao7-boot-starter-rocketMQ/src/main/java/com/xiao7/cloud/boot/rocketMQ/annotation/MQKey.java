package com.xiao7.cloud.boot.rocketMQ.annotation;

import java.lang.annotation.*;

/**
 * 用来标识作为消息key的字段
 * prefix 会作为前缀拼到字段值前面
 *
 * @author xiao7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MQKey {
    String prefix() default "";
}
