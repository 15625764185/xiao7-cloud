package com.xiao7.cloud.boot.redis.config;

import com.xiao7.cloud.boot.redis.cluster.RedisCache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ：xiao7
 * @date ：Created in 2020/6/10 20:55
 * @description：缓存工具类
 */
@Slf4j
@Data
public class RedisCacheTool<T> implements RedisCache<T> {
  @Autowired private RedisTemplate<String, Object> redisTemplate;

  @Override
  public int getSize() {
    Long size = redisTemplate.execute(RedisServerCommands::dbSize);
    assert size != null;
    return size.intValue();
  }

  @Override
  public void stringBatchDelete(String cachePrefix) {
    try {
      Set<String> keys = redisTemplate.keys(cachePrefix + "*");
      assert keys != null;
      keys.forEach(this::del);
    } catch (Exception e) {
      log.error("redis stringBatchDelete {}", cachePrefix);
    }
  }

  @Override
  public void hashAdd(String key, String field, T value) {
    try {
      redisTemplate.opsForHash().put(key, field, value);
    } catch (Exception e) {
      log.error("redis hashAdd {}, {}, {}", key, field, value);
    }
  }

  @Override
  public T hashGetByKeyField(String key, String field) {
    try {
      return (T) redisTemplate.opsForHash().get(key, field);
    } catch (Exception e) {
      log.error("redis hashGetByKeyField {}, {}", key, field);
    }
    return null;
  }

  @Override
  public void set(String key, Object obj) {
    try {
      redisTemplate.opsForValue().set(key, obj);
    } catch (Exception e) {
      log.error("redis set " + key + " error ,{}", e.getMessage());
    }
  }

  @Override
  public Boolean setNx(String key, Object obj) {
    try {
      return redisTemplate.opsForValue().setIfAbsent(key, obj);
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void set(String key, Object obj, long expireSec, TimeUnit timeUnit) {
    try {
      redisTemplate.boundValueOps(key).set(obj);
      expire(key, expireSec, timeUnit);
    } catch (Exception e) {
      log.error("redis set " + key + "error ,{}", e.getMessage());
    }
  }

  @Override
  public Boolean setNx(String key, Object obj, long expireSec, TimeUnit timeUnit) {
    try {
      redisTemplate.opsForValue().setIfAbsent(key, obj);
      expire(key, expireSec, timeUnit);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public T get(String key) {
    try {
      return (T) redisTemplate.opsForValue().get(key);
    } catch (Exception e) {
      log.error("redis set " + key + "error ,{}", e.getMessage());
    }
    return null;
  }

  @Override
  public boolean exists(String key) {
    try {
      return Optional.ofNullable(redisTemplate.hasKey(key)).orElse(false);
    } catch (Exception e) {
      log.error("redis exists " + key + "error ,{}", e.getMessage());
    }
    return false;
  }

  @Override
  public String getSet(String key, String value) {
    return null;
  }

  @Override
  public void del(String key) {
    try {
      redisTemplate.delete(key);
    } catch (Exception e) {
      log.error("redis del " + key + "error ,{}", e.getMessage());
    }
  }

  @Override
  public void lPush(String key, String value) {}

  @Override
  public void lMSet(String key, List values, long expireSec, TimeUnit timeUnit) {}

  @Override
  public String lPop(String key) {
    return null;
  }

  @Override
  public long lLen(String key) {
    return 0;
  }

  @Override
  public List<String> lGet(String key) {
    return null;
  }

  @Override
  public void smSet(String key, List values, long expireSec, TimeUnit timeUnit) {}

  @Override
  public void sSet(String key, String value) {}

  @Override
  public String sPop(String key) {
    return null;
  }

  @Override
  public long sLen(String key) {
    return 0;
  }

  @Override
  public long sSel(String key, String... values) {
    return 0;
  }

  @Override
  public Set<String> sGet(String key) {
    return null;
  }

  @Override
  public void hSet(String key, String field, String value) {}

  @Override
  public String hGet(String key, String field) {
    return null;
  }

  @Override
  public Map<String, String> hGetAll(String key) {
    return null;
  }

  @Override
  public boolean hExists(String key, String field) {
    return false;
  }

  @Override
  public Set<String> hKeys(String key) {
    return null;
  }

  @Override
  public Set<String> hValues(String key) {
    return null;
  }

  @Override
  public long hLen(String key) {
    return 0;
  }

  @Override
  public void hDel(String key, String field) {}

  @Override
  public long incrBy(String key, long incrValue) {
    return 0;
  }

  @Override
  public long hashIncrBy(String h, String hk, long incrValue) {
    return 0;
  }

  @Override
  public long decrBy(String key, long decrValue) {
    return 0;
  }

  @Override
  public long hashDecrBy(String h, String hk, long decrValue) {
    return 0;
  }

  @Override
  public void expire(String key, long expireSec, TimeUnit timeUnit) {
    try {
      if (expireSec != 0) {
        if (Objects.isNull(timeUnit)) redisTemplate.expire(key, expireSec, TimeUnit.SECONDS);
        else redisTemplate.expire(key, expireSec, timeUnit);
      }
    } catch (Exception e) {
      log.error("error|redis,调用方法 {} 失败,key {}, expireSec {} ", "expire", key, expireSec);
    }
  }

  @Override
  public long ttl(String key) {
    return 0;
  }

  @Override
  public void hmSet(String key, Map items) {}

  @Override
  public void hmSet(String key, Map items, long expireSec) {}
}
