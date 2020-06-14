package com.xiao7.cloud.rocketMQ;

import com.xiao7.cloud.boot.base.aop.Xiao7Application;
import com.xiao7.cloud.boot.rocketMQ.annotation.EnableRocketMQ;
import org.springframework.boot.SpringApplication;

@Xiao7Application(appName = "rocketMq-example", port = "8081")
@EnableRocketMQ
public class RocketMqApplication {
  public static void main(String[] args) {
    SpringApplication.run(RocketMqApplication.class, args);
  }
}
