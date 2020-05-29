package com.xiao7.cloud.boot.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * mybatis属性配置
 *
 * @author cwh
 */
@Data
@ConfigurationProperties(prefix = MybatisProperties.PREFIX)
public class MybatisProperties implements Serializable {
  public static final String PREFIX = "xiao7.mybatis";

  /** 数据版本 */
  private String version = "1";

  /** 使用mybaits starter */
  private boolean enable = true;

  /** 启动自动填充功能：更新/修改 会自动填充创建时间，修改时间 */
  private boolean autoFill = true;

  /** 单页限制 500 条，小于 0 如 -1 不受限制 */
  private long limit = 500;
}
