package com.codestepfish.redis.config;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

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
        return new RedissonSpringCacheManager(redissonClient);
    }
}
