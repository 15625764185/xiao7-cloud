package com.xiao7.cloud.boot.jdbc.annotation;

import com.xiao7.cloud.boot.jdbc.aspect.DataSourceAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 多数据源注解
 *
 * @author xiao7
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {DataSourceAspect.class})
@Documented
public @interface DataSource {
  String name() default "";
}
