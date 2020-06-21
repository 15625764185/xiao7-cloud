package com.xiao7.cloud.springcloud;

import com.xiao7.cloud.boot.base.aop.Xiao7Application;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
//@RefreshScope //支持Nacos的动态刷新功能。
@Xiao7Application(appName = "nacos-config-client", port = "8081", env = "info")
public class SpringCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }
}
