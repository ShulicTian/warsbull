package com.warsbull.system.common.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;

/**
 * Redis工具类
 *
 * @author ShulicTian
 * @date 2020/01/09
 */
public class RedisUtils {
    private static RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate");
    private static StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);

    public static String test() {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get("fanz") + "";
    }

    public static String test2() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        return operations.get("name");
    }

}
