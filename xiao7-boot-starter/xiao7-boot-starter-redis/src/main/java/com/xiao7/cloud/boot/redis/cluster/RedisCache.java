package com.xiao7.cloud.boot.redis.cluster;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务
 *
 * @author cwh
 */
public interface RedisCache<T> {
  /**
   * 缓存大小 mybits 二级缓存需要
   *
   * @return
   */
  int getSize();

  /**
   * 模糊批量删除
   *
   * @param cachePrefix
   */
  void stringBatchDelete(String cachePrefix);

  /**
   * 设置Hash集合field对应的value
   *
   * @param key key
   * @param field field
   * @param value value
   */
  void hashAdd(String key, String field, T value);

  /**
   * 获取Hash集合field对应的value
   *
   * @param key key
   * @param field field
   * @return field对应的value，不存在时返回null
   */
  T hashGetByKeyField(String key, String field);

  /**
   * 添加缓存
   *
   * @param key
   * @param obj
   */
  void set(String key, T obj);

  /**
   * 添加缓存
   *
   * @param key key
   * @param obj value
   * @param expireSec 失效时长
   * @param timeUnit 时分秒
   */
  void set(String key, T obj, long expireSec, TimeUnit timeUnit);

  /**
   * 添加缓存
   *
   * @param key
   * @param obj
   */
  Boolean setNx(String key, T obj);

  /**
   * 添加缓存
   *
   * @param key key
   * @param obj value
   * @param expireSec 失效时长
   * @param timeUnit 时分秒
   */
  Boolean setNx(String key, T obj, long expireSec, TimeUnit timeUnit);

  /**
   * 读取缓存
   *
   * @param key
   * @return
   */
  T get(String key);

  /**
   * key是否存在
   *
   * @param key key
   * @return 是否存在
   */
  boolean exists(String key);

  /**
   * 设置字符串值，并返回其旧值，不存在时返回null
   *
   * @param key key
   * @param value value
   */
  String getSet(String key, String value);

  /**
   * 删除key
   *
   * @param key key
   */
  void del(String key);

  /**
   * 添加列表值
   *
   * @param key key
   * @param value value
   */
  void lPush(String key, String value);

  /**
   * 添加列表
   *
   * @param key key
   * @param values values
   * @param expireSec 过期时间(seconds)，0表示永不过期
   */
  void lMSet(String key, List<T> values, long expireSec, TimeUnit timeUnit);

  /**
   * 弹出栈顶的列表值 注意，Redis的列表是栈结构，先进后出
   *
   * @param key key
   * @return 栈顶的列表值
   */
  String lPop(String key);

  /**
   * 获取列表值的长度
   *
   * @param key key
   * @return 长度
   */
  long lLen(String key);

  /**
   * 获取列表中的所有值
   *
   * @param key key
   * @return 值列表
   */
  List<String> lGet(String key);

  /**
   * 添加Set集合
   *
   * @param key key
   * @param values values
   * @param expireSec 过期时间(seconds)，0表示永不过期
   */
  void smSet(String key, List<T> values, long expireSec, TimeUnit timeUnit);

  /**
   * 设置Set集合
   *
   * @param key key
   * @param value value
   */
  void sSet(String key, String value);

  /**
   * 返回一个随机的成员值
   *
   * @param key key
   * @return 返回值
   */
  String sPop(String key);

  /**
   * 获取Set集合的长度
   *
   * @param key key
   * @return 长度
   */
  long sLen(String key);

  /**
   * 删除Set集合对应的values
   *
   * @param key key
   * @param values values
   * @return 影响的行数
   */
  long sSel(String key, String... values);

  /**
   * 返回set集合
   *
   * @param key key
   * @return 值集合
   */
  Set<String> sGet(String key);

  /**
   * 设置Hash集合
   *
   * @param key key
   * @param items items
   * @param expireSec 过期时间(seconds)，0表示永不过期
   */
  void hmSet(String key, Map<String, String> items, long expireSec);

  /**
   * 设置Hash集合
   *
   * @param key key
   * @param items items
   */
  void hmSet(String key, Map<String, String> items);

  /**
   * 设置Hash集合field对应的value
   *
   * @param key key
   * @param field field
   * @param value value
   */
  void hSet(String key, String field, String value);

  /**
   * 获取Hash集合field对应的value
   *
   * @param key key
   * @param field field
   * @return field对应的value，不存在时返回null
   */
  String hGet(String key, String field);

  /**
   * 获取Hash集合的所有items
   *
   * @param key key
   * @return 所有items
   */
  Map<String, String> hGetAll(String key);

  /**
   * 判断Hash集合field是否存在
   *
   * @param key key
   * @param field field
   * @return 是否存在
   */
  boolean hExists(String key, String field);

  /**
   * 获取Hash集合的所有keys
   *
   * @param key key
   * @return 所有keys
   */
  Set<String> hKeys(String key);

  /**
   * 获取Hash集合的所有values
   *
   * @param key key
   * @return 所有values
   */
  Set<String> hValues(String key);

  /**
   * 获取Hash集合的长度
   *
   * @param key key
   * @return 长度
   */
  long hLen(String key);

  /**
   * 删除Hash集合是对应的field
   *
   * @param key key
   * @param field field
   */
  void hDel(String key, String field);

  /**
   * 原子加操作
   *
   * @param key key，key不存在时会自动创建值为0的对象
   * @param incrValue 要增加的值，必须是Long Int Float 或 Double
   * @return 操作后的值
   */
  long incrBy(String key, long incrValue);

  /**
   * Hash原子加操作
   *
   * @param h h
   * @param hk hk
   * @param incrValue 要增加的值，必须是Long Int Float 或 Double
   * @return 操作后的值
   */
  long hashIncrBy(String h, String hk, long incrValue);

  /**
   * 原子减操作
   *
   * @param key key不存在时会自动创建值为0的对象
   * @param decrValue 要减少的值，必须是Long 或 Int
   * @return 操作后的值
   */
  long decrBy(String key, long decrValue);

  /**
   * 原子减操作
   *
   * @param h h
   * @param hk hk
   * @param decrValue 要减少的值，必须是Long 或 Int
   * @return 操作后的值
   */
  long hashDecrBy(String h, String hk, long decrValue);

  /**
   * 设置过期时间
   *
   * @param key key
   * @param expireSec 过期时间(seconds)，0表示永不过期
   */
  void expire(String key, long expireSec, TimeUnit timeUnit);

  /**
   * 获取过期时间（秒）
   *
   * @param key key
   * @return -2 key不存在，-1 对应的key永不过期，正数 过期时间(seconds)
   */
  long ttl(String key);
}
