package com.xiao7.cloud.boot.jdbc.aspect;

import com.xiao7.cloud.boot.jdbc.annotation.DataSource;
import com.xiao7.cloud.boot.jdbc.datasource.DataSourceConstant;
import com.xiao7.cloud.boot.jdbc.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面设置数据源
 *
 * @author xiao7
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect {

  @Pointcut("@annotation(com.xiao7.cloud.boot.jdbc.annotation.DataSource)")
  public void dataSourcePointCut() {
  }

  @Around("dataSourcePointCut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    DataSource ds = method.getAnnotation(DataSource.class);
    if (ds == null) {
      DynamicDataSource.setDataSource(DataSourceConstant.DEFAULT_DATASOURCE);
      log.info("set dynamic datasource is " + DataSourceConstant.DEFAULT_DATASOURCE);
    } else {
      DynamicDataSource.setDataSource(ds.name());
      log.info("set dynamic datasource is " + ds.name());
    }

    try {
      return point.proceed();
    } finally {
      DynamicDataSource.clear();
      log.info("clear dynamic datasource");
    }
  }
}
