package com.xiao7.cloud.boot.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redis 属性配置
 *
 * @author cwh
 */
@ConfigurationProperties(prefix = RedisProperties.PREFIX)
@Data
public class RedisProperties {
    public static final String PREFIX = "xiao7.redis";

    public static final String CACHE_PREFIX = "xiao7.redis.cache";
    /**
     * 是否启用
     */
    private boolean enable = true;
    /**
     * value jackson序列化支持
     */
    private String serialization = "jackson";

    private Cache cache = new Cache();

    public static class Cache {
        private boolean enable = true;
        /**
         * 缓存失效时间
         */
        private long expire = 1000 * 60 * 60;

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }


}
