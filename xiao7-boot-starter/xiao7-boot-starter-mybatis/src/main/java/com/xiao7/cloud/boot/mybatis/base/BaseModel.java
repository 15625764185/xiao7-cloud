package com.xiao7.cloud.boot.mybatis.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础属性 框架所有Entity都需要继承的model
 *
 * @author xiao7
 */
@Data
public abstract class BaseModel implements Serializable {

  /** 主键ID */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;

  /** 创建时间 */
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  /** 更新时间 */
  @TableField(value = "update_time", fill = FieldFill.UPDATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  /** 数据库版本 */
  @TableField(value = "version", fill = FieldFill.INSERT)
  private String version;

  /** 是否删除 */
  @TableField(value = "is_delete", fill = FieldFill.INSERT)
  @TableLogic
  private Integer isDelete;
}
