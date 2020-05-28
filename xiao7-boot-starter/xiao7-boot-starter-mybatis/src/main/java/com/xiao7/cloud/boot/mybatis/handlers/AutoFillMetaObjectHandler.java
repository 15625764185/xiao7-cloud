package com.xiao7.cloud.boot.mybatis.handlers;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xiao7.cloud.boot.mybatis.config.MybatisProperties;
import com.xiao7.cloud.boot.mybatis.constant.TableColConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * 自动填充数据
 *
 * @author cwh
 */
@AutoConfigureAfter(MybatisProperties.class)
@Configuration
@Slf4j
@Data
public class AutoFillMetaObjectHandler implements MetaObjectHandler {

  private MybatisProperties properties;

  public void insertFill(MetaObject metaObject) {
    log.info("start insert fill ....");
    if (properties.isAutoFill()) {
      this.fillStrategy(metaObject, TableColConstant.CREATE_TIME, DateUtil.now());
    }
  }

  public void updateFill(MetaObject metaObject) {
    if (properties.isAutoFill()) {
      log.info("start update fill ....");
      this.fillStrategy(metaObject, TableColConstant.UPDATE_TIME, DateUtil.now());
    }
  }
}
