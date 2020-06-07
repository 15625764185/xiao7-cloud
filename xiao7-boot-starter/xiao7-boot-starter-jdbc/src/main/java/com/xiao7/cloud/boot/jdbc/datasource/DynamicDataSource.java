package com.xiao7.cloud.boot.jdbc.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源配置
 *
 * @author xiao7
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
  private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

  public DynamicDataSource() {
  }

  public DynamicDataSource(
          DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
    super.setDefaultTargetDataSource(defaultTargetDataSource);
    super.setTargetDataSources(targetDataSources);
    super.afterPropertiesSet();
  }

  /**
   * 设置数据源
   *
   * @param dataSource
   */
  public static void setDataSource(String dataSource) {
    contextHolder.set(dataSource);
  }

  /**
   * 删除数据源
   */
  public static void clear() {
    contextHolder.remove();
  }

  /**
   * 重写这个方法，这里返回使用的数据源 key 值,用户切换数据源
   */
  @Override
  protected Object determineCurrentLookupKey() {
    return contextHolder.get();
  }

  /**
   * 添加数据源
   *
   * @param key
   * @param dataSource
   */
  public void addDataSource(String key, Object dataSource) {
    try {
      Map<Object, Object> targetDataSources = new HashMap<>();
      targetDataSources.put(key, dataSource);
      setTargetDataSources(targetDataSources);
    } catch (Exception e) {
      throw new RuntimeException("添加数据源失败");
    }
  }
}
