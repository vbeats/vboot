package com.bootvue.core.config.redis;

import com.bootvue.core.config.app.AppConfig;
import com.bootvue.core.config.app.Cache;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableCaching
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisCacheManager {
    private final AppConfig appConfig;

    @Bean
    @Primary
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
        // 过期时间   最长空闲时间
        Set<Cache> caches = appConfig.getCache();
        if (!CollectionUtils.isEmpty(caches)) {
            caches.forEach(it -> config.put("cache:" + it.getCacheName(), new CacheConfig(it.getTtl(), it.getMaxIdleTime())));
        }
        return new RedissonSpringCacheManager(redissonClient, config);
    }

}
