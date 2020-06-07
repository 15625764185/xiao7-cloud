package com.xiao7.cloud.boot.jdbc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * druid 默认初始化属性
 *
 * @author xiao7
 */
@Getter
@Setter
@ToString
public class DruidInitModel {
  private int initialSize = 10;
  private int maxActive = 100;
  private int minIdle = 10;
  private int maxWait = 60000;
  private boolean poolPreparedStatements = true;
  private int maxPoolPreparedStatementPerConnectionSize = 20;
  private int timeBetweenEvictionRunsMillis = 60000;
  private int minEvictableIdleTimeMillis = 300000;
  private boolean testWhileIdle = true;
  private boolean testOnBorrow = false;
  private boolean testOnReturn = false;
  private boolean logSlowSql = true;
  private int slowSqlMillis = 1000;
  private boolean mergeSql = false;
  private boolean wallMultiStatementAllow = true;
}
