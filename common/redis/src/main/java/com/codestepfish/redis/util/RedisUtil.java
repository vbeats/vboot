package com.codestepfish.redis.util;

import cn.hutool.extra.spring.SpringUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.time.Duration;

public class RedisUtil {
    private static final RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);

    /**
     * 如果不存在则设置 并返回 true 如果存在则返回 false
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return set成功或失败
     */
    public static <T> boolean setIfAbsent(final String key, final T value, final Duration duration) {
        RBucket<T> bucket = getBucket(key);
        return bucket.setIfAbsent(value, duration);
    }

    private static <T> RBucket<T> getBucket(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket;
    }

    /**
     * 删除单个对象
     *
     * @param key 缓存的键值
     */
    public static boolean delete(final String key) {
        return redissonClient.getBucket(key).delete();
    }

    public static <T> T get(final String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }
}
