package com.xiao7.cloud.demo;

import com.xiao7.cloud.boot.base.aop.Xiao7Application;
import org.springframework.boot.SpringApplication;

@Xiao7Application(appName = "example", port = "8081")
public class SpringbootDemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringbootDemoApplication.class, args);
  }
}
