package com.xiao7.cloud.boot.mybatis.constant;
/**
 * 公用的表字段常量类
 *
 * @desc 用于sql过滤指定字段值
 * @author cwh
 */
public interface TableColConstant {

  /** 更新时间 */
  String UPDATE_TIME = "updateTime";

  /** 创建时间 */
  String CREATE_TIME = "createTime";

  /** 数据库版本 */
  String DB_VERSION = "version";

  /** 数据库版本 */
  String IS_DELETE = "isDelete";
}
