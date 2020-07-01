package com.xiao7.cloud.boot.jdbc.config;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.xiao7.cloud.boot.jdbc.datasource.DynamicDataSource;
import com.xiao7.cloud.boot.jdbc.model.DruidInitModel;
import com.xiao7.cloud.boot.jdbc.model.MultiDataSourceModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置多数据源
 *
 * @author xiao7
 */
@EnableConfigurationProperties(value = {DataSourceProperties.class})
@AutoConfigureBefore(
        value = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@AutoConfigureAfter(value = {DataSourceProperties.class})
@Configuration
@Slf4j
@Getter
public class DataSourceConfig implements InitializingBean {
  @Autowired
  private DataSourceProperties properties;

  /**
   * 配置druid管理
   *
   * @return
   */
  @Bean
  public ServletRegistrationBean druidServlet() {
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), properties.getViewUrlPath());
    servletRegistrationBean.addInitParameter("loginUsername", properties.getLoginUsername());
    servletRegistrationBean.addInitParameter("loginPassword", properties.getLoginPassword());
    return servletRegistrationBean;
  }

  /**
   * 配置druid管理页面静态资源不拦截
   *
   * @return
   */
  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new WebStatFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
    return filterRegistrationBean;
  }

  /**
   * 配置动态数据源
   *
   * @return DynamicDataSource
   */
  @Bean
  @Primary
  @ConditionalOnProperty(value = "xiao7.datasource.enable", matchIfMissing = true)
  public DynamicDataSource dataSource() {
    Map<Object, Object> targetDataSources = new HashMap<>();
    DataSource defaultTargetDataSource = assembleDatasource(new MultiDataSourceModel(properties.getUsername(), properties.getPassword(), properties.getUrl(), properties.getDriverClassName()));
    List<MultiDataSourceModel> multi = properties.getMulti();
    if (BeanUtil.isNotEmpty(multi))
      multi.forEach(e -> targetDataSources.put(e.getName(), assembleDatasource(e)));
    return new DynamicDataSource(defaultTargetDataSource, targetDataSources);
  }

  /**
   * 组装数据源对象
   *
   * @param jdbc
   * @return
   */
  public DataSource assembleDatasource(MultiDataSourceModel jdbc) {
    DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
    DruidInitModel initConfig = properties.getGlobConfig();
    dataSource.setUsername(jdbc.getUsername());
    dataSource.setPassword(jdbc.getPassword());
    dataSource.setUrl(jdbc.getUrl());
    dataSource.setDriverClassName(jdbc.getDriverClassName());
    dataSource.setInitialSize(initConfig.getInitialSize());
    dataSource.setMaxActive(initConfig.getMaxActive());
    dataSource.setMinIdle(initConfig.getMinIdle());
    dataSource.setMaxWait(initConfig.getMaxWait());
    dataSource.setPoolPreparedStatements(initConfig.isPoolPreparedStatements());
    dataSource.setMaxPoolPreparedStatementPerConnectionSize(
            initConfig.getMaxPoolPreparedStatementPerConnectionSize());
    dataSource.setTimeBetweenEvictionRunsMillis(initConfig.getTimeBetweenEvictionRunsMillis());
    dataSource.setMinEvictableIdleTimeMillis(initConfig.getMinEvictableIdleTimeMillis());
    dataSource.setTestWhileIdle(initConfig.isTestWhileIdle());
    dataSource.setTestOnBorrow(initConfig.isTestOnBorrow());
    dataSource.setTestOnReturn(initConfig.isTestOnReturn());
    return dataSource;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("数据源配置加载完成,配置信息: {}", properties);
  }
}
