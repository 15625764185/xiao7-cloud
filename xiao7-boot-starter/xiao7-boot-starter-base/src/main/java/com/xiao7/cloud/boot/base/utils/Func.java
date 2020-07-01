package com.xiao7.cloud.boot.base.utils;

import java.util.Objects;

/**
 * 工具包集合，只做简单的调用，不删除原有工具类
 *
 * @author cwh
 */
public class Func {

  /**
   * 比较两个对象是否相等。<br>
   * 相同的条件有两个，满足其一即可：<br>
   *
   * @param obj1 对象1
   * @param obj2 对象2
   * @return 是否相等
   */
  public static boolean equals(Object obj1, Object obj2) {
    return Objects.equals(obj1, obj2);
  }
  /**
   * 强转string,并去掉多余空格
   *
   * @param str 字符串
   * @return String
   */
  public static String toStr(Object str) {
    return toStr(str, "");
  }

  /**
   * 强转string,并去掉多余空格
   *
   * @param str 字符串
   * @param defaultValue 默认值
   * @return String
   */
  public static String toStr(Object str, String defaultValue) {
    if (null == str) {
      return defaultValue;
    }
    return String.valueOf(str);
  }
}
