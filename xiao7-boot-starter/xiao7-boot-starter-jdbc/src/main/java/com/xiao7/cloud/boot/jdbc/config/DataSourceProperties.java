package com.xiao7.cloud.boot.jdbc.config;

import com.xiao7.cloud.boot.jdbc.model.DruidInitModel;
import com.xiao7.cloud.boot.jdbc.model.MultiDataSourceModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 数据源配置类
 *
 * @author xiao7
 */
@ConfigurationProperties(DataSourceProperties.PREFIX)
@Data
public class DataSourceProperties {
  public static final String PREFIX = "xiao7.datasource";

  /** 是否启用 */
  private boolean enable = true;

  /** 数据连接池类型 目前只支持druid */
  private String type = "com.alibaba.druid.pool.DruidDataSource";

  /** 用户名称 */
  private String username;

  /** 用户密码 */
  private String password;

  /** url地址 */
  private String url;

  /** 驱动类名 默认mysql 8.0 default com.mysql.cj.jdbc.Driver */
  private String driverClassName = "com.mysql.cj.jdbc.Driver";

  /** 多数据源配置 */
  private List<MultiDataSourceModel> multi;

  /** 管理界面地址 */
  private String viewUrlPath = "/druid/*";

  /** 登录账号 默认:admin */
  private String loginUsername = "admin";

  /** 登录密码 */
  private String loginPassword = "admin";

  /** 全局属性配置 */
  private DruidInitModel globConfig = new DruidInitModel();
}
