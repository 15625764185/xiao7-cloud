package com.xiao7.cloud.boot.base.listener;

import cn.hutool.core.bean.BeanUtil;
import com.xiao7.cloud.boot.base.aop.Xiao7Application;
import com.xiao7.cloud.boot.base.utils.Func;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 环境准备完成启动监听类 启动类添加 @BifrostApplication 注解
 *
 * @author cwh
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class Xiao7ApplicationListener
    implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  /** active profile 默认配置，不配置则默认注解配置 */
  public static final String ACTIVE_PROFILES_PROPERTY = "spring.profiles.active";

  public static final String APPLICATION_NAME = "spring.application.name";
  public static final String SERVER_PORT = "server.port";
  private static AtomicBoolean processed = new AtomicBoolean(false);

  /**
   * 初始化自定义的环境属性
   *
   * @param event
   */
  private void initEnvProperties(ApplicationEnvironmentPreparedEvent event) {
    // 获取 Xiao7Application 注解
    Class<?> application = event.getSpringApplication().getMainApplicationClass();
    Annotation annotation = application.getAnnotation(Xiao7Application.class);
    if (BeanUtil.isEmpty(annotation)) {
      return;
    }
    String appName = Func.toStr(getAnnotationValue(annotation, Xiao7Application.APP_NAME), null);
    String port = Func.toStr(getAnnotationValue(annotation, Xiao7Application.PORR), null);
    String env = Func.toStr(getAnnotationValue(annotation, Xiao7Application.ENV), null);
    // jar 启动传参数, 使用传入参数
    if (BeanUtil.isNotEmpty(System.getProperty(ACTIVE_PROFILES_PROPERTY))) {
      env = System.getProperty(ACTIVE_PROFILES_PROPERTY);
    }
    // 应用名称
    if (BeanUtil.isNotEmpty(System.getProperty(APPLICATION_NAME))) {
      appName = System.getProperty(APPLICATION_NAME);
    }
    // 端口
    if (BeanUtil.isNotEmpty(System.getProperty(SERVER_PORT))) {
      port = System.getProperty(SERVER_PORT);
    }
    /* 配置启动环境 */
    ConfigurableEnvironment configurableEnvironment = event.getEnvironment();
    configurableEnvironment.setActiveProfiles(env);
    System.setProperty(APPLICATION_NAME, appName);
    System.setProperty(SERVER_PORT, port);
    System.setProperty(ACTIVE_PROFILES_PROPERTY, env);
  }

  /**
   * 获取该注解对象的属性值
   *
   * @param annotation 注解对象
   * @param property 属性值
   * @return
   */
  private Object getAnnotationValue(Annotation annotation, String property) {
    Object result = null;
    if (annotation != null) {
      // 获取被代理的对象
      InvocationHandler invo = Proxy.getInvocationHandler(annotation);
      Map map = (Map) getFieldValue(invo, "memberValues");
      if (map != null) {
        result = map.get(property);
      }
    }
    return result;
  }

  private <T> Object getFieldValue(T object, String property) {
    if (object != null && property != null) {
      Class<T> currClass = (Class<T>) object.getClass();

      try {
        Field field = currClass.getDeclaredField(property);
        field.setAccessible(true);
        return field.get(object);
      } catch (NoSuchFieldException e) {
        throw new IllegalArgumentException(currClass + " has no property: " + property);
      } catch (IllegalArgumentException e) {
        throw e;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    // Skip if processed before, prevent duplicated execution in Hierarchical ApplicationContext
    if (processed.get()) {
      return;
    }
    initEnvProperties(event);
    // mark processed to be true
    processed.compareAndSet(false, true);
  }
}
