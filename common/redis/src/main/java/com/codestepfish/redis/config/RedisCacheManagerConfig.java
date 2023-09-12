package com.codestepfish.redis.config;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonCache;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
@EnableCaching
@Slf4j
public class RedisCacheManagerConfig {

    // 生成yaml配置
    public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                .setDatabase(0).setKeepAlive(true)
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(32)
                .setTimeout(1000);
        config.setCodec(new JsonJacksonCodec());
        log.info("config: {}", config.toYAML());
    }

    @Bean
    @Primary
    CacheManager cacheManager(RedissonClient redissonClient) {
        return new RedissonSpringCacheManager(redissonClient) {

            final Map<String, CacheConfig> configMap = new ConcurrentHashMap<>();
            final ConcurrentMap<String, Cache> instanceMap = new ConcurrentHashMap<>();
            private boolean transactionAware = true;

            @Override
            public Cache getCache(String name) {
                // 重写 cacheName 支持多参数
                String[] array = StringUtils.delimitedListToStringArray(name, "#");
                name = array[0];

                Cache cache = instanceMap.get(name);
                if (!ObjectUtils.isEmpty(cache)) {
                    return cache;
                }

                CacheConfig config = configMap.get(name);
                if (config == null) {
                    config = createDefaultConfig();
                    configMap.put(name, config);
                }

                if (array.length > 1) {
                    config.setTTL(DurationStyle.detectAndParse(array[1]).toMillis());
                }
                if (array.length > 2) {
                    config.setMaxIdleTime(DurationStyle.detectAndParse(array[2]).toMillis());
                }
                if (array.length > 3) {
                    config.setMaxSize(Integer.parseInt(array[3]));
                }

                if (config.getMaxIdleTime() == 0 && config.getTTL() == 0 && config.getMaxSize() == 0) {
                    return createMap(name, config);
                }

                return createMapCache(name, config);
            }

            @Override
            public void setTransactionAware(boolean transactionAware) {
                this.transactionAware = transactionAware;
            }

            private Cache createMap(String name, CacheConfig config) {
                RMap<Object, Object> map = redissonClient.getMap(name);

                Cache cache = new RedissonCache(map, false);
                if (transactionAware) {
                    cache = new TransactionAwareCacheDecorator(cache);
                }
                Cache oldCache = instanceMap.putIfAbsent(name, cache);
                if (oldCache != null) {
                    cache = oldCache;
                }
                return cache;
            }

            private Cache createMapCache(String name, CacheConfig config) {
                RMapCache<Object, Object> map = redissonClient.getMapCache(name);

                Cache cache = new RedissonCache(map, config, false);
                if (transactionAware) {
                    cache = new TransactionAwareCacheDecorator(cache);
                }
                Cache oldCache = instanceMap.putIfAbsent(name, cache);
                if (oldCache != null) {
                    cache = oldCache;
                } else {
                    map.setMaxSize(config.getMaxSize());
                }
                return cache;
            }
        };
    }
}
