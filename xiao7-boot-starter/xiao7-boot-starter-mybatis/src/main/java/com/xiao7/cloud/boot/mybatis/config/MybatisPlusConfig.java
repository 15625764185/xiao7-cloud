package com.xiao7.cloud.boot.mybatis.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.xiao7.cloud.boot.mybatis.plugins.sqllog.SqlLogInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置
 *
 * @author lcb
 */
@Configuration
@EnableConfigurationProperties(value = {MybatisProperties.class})
@AutoConfigureAfter(MybatisProperties.class)
@MapperScan(value = "com.xiao7.cloud.**.mapper.**")
public class MybatisPlusConfig {

  @Autowired private MybatisProperties properties;

  /**
   * 乐观锁插件
   *
   * @return
   */
  @Bean
  public OptimisticLockerInterceptor OptimisticLockerInterceptor() {
    return new OptimisticLockerInterceptor();
  }

  /** sql 日志 */
  @Bean
  @ConditionalOnProperty(value = "xiao7.mybaits.enable-sql-log", matchIfMissing = true)
  public SqlLogInterceptor sqlLogInterceptor() {
    return new SqlLogInterceptor();
  }

  /** 分页插件 */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    paginationInterceptor.setLimit(properties.getLimit());
    return paginationInterceptor;
  }

  /** 注入主键生成器 */
  @Bean
  public IKeyGenerator keyGenerator() {
    return new H2KeyGenerator();
  }
}
