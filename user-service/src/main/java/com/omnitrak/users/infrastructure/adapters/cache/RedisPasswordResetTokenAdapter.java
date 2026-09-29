package com.omnitrak.users.infrastructure.adapters.cache;

import com.omnitrak.users.domain.ports.out.auth.PasswordResetTokenPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Component
public class RedisPasswordResetTokenAdapter implements PasswordResetTokenPort {

    private static final String KEY_PREFIX = "reset_token:";
    private final StringRedisTemplate redisTemplate;

    public RedisPasswordResetTokenAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveResetToken(String email, String token, long ttlInMinutes){
        String key  = KEY_PREFIX + token;
        redisTemplate.opsForValue().set(key, email, ttlInMinutes, TimeUnit.MINUTES);
    }

    @Override
    public Optional<String> getEmailByResetToken(String token){
        String key = KEY_PREFIX + token;
        String email = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(email);
    }

    @Override
    public void deleteResetToken(String token){
        String key  = KEY_PREFIX + token;
        redisTemplate.delete(key);
    }
}
