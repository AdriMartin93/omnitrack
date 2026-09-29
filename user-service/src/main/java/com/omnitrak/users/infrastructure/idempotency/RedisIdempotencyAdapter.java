package com.omnitrak.users.infrastructure.idempotency;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisIdempotencyAdapter {

    private static final String KEY_PREFIX = "idempotency:";
    private static final long TTL_HOURS = 24;

    private final StringRedisTemplate redisTemplate;

    public RedisIdempotencyAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean lockKey(String key) {
        String redisKey = KEY_PREFIX + key;

        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(redisKey, "PROCESSING", TTL_HOURS, TimeUnit.HOURS);
        return Boolean.TRUE.equals(acquired);
    }

    public void saveResponse(String key, String responseBody) {
        String redisKey = KEY_PREFIX + key;
        redisTemplate.opsForValue().set(redisKey, responseBody, TTL_HOURS, TimeUnit.HOURS);
    }

    public Optional<String> getResponse(String key) {
        String redisKey = KEY_PREFIX + key;
        String cached = redisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(cached);
    }
}
