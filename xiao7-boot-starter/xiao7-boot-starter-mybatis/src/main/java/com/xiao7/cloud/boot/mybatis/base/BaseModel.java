package com.xiao7.cloud.boot.mybatis.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础属性 框架所有Entity都需要继承的model
 *
 * @author lcb
 * @date 2018/12/26 17:42
 */
@Data
public abstract class BaseModel implements Serializable {

  /** 主键ID */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;

  /** 创建时间 */
  @TableField(fill = FieldFill.INSERT)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  /** 更新时间 */
  @TableField(fill = FieldFill.UPDATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

  /** 数据库版本 */
  @TableField(value = "version", fill = FieldFill.INSERT)
  private String version;

  /** 是否删除 */
  @TableLogic private int isDelete;
}
