package com.xiao7.cloud.boot.jdbc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 多数据源配置信息
 *
 * @author xiao7
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MultiDataSourceModel implements Serializable {
  /** 数据源名称 @Datasource name 使用 */
  private String name;
  /** 用户名称 */
  private String username;
  /** 用户密码 */
  private String password;
  /** url地址 */
  private String url;
  /** 驱动类名 default com.mysql.cj.jdbc.Driver */
  private String driverClassName = "com.mysql.cj.jdbc.Driver";

  public MultiDataSourceModel(
      String username, String password, String url, String driverClassName) {
    this.username = username;
    this.password = password;
    this.url = url;
    this.driverClassName = driverClassName;
  }
}
