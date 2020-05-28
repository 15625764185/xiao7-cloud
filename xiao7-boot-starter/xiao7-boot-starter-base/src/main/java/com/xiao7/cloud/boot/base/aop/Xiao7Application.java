package com.xiao7.cloud.boot.base.aop;

import com.xiao7.cloud.boot.base.constant.AppConstant;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

/**
 * Bifrost启动注解
 *
 * @desc 所有工程必须添加该注解，通过注解加载自定义的初始化参数
 * @author cwh
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.CUSTOM,
          classes = {TypeExcludeFilter.class}),
      @ComponentScan.Filter(
          type = FilterType.CUSTOM,
          classes = {AutoConfigurationExcludeFilter.class})
    })
@EnableTransactionManagement
public @interface Xiao7Application {

  String APP_NAME = "appName";
  String PORR = "port";
  String ENV = "env";

  /**
   * 应用名称
   *
   * @return
   */
  String appName();

  /**
   * 端口
   *
   * @return
   */
  String port() default "8080";

  /**
   * 环境名
   *
   * @return
   */
  String env() default AppConstant.ENV_DEV;
}
