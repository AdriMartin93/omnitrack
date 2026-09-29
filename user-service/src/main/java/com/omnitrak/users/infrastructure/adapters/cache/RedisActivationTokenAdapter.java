package com.omnitrak.users.infrastructure.adapters.cache;

import com.omnitrak.users.domain.ports.out.auth.ActivationTokenPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Component
public class RedisActivationTokenAdapter implements ActivationTokenPort {

    private static final String KEY_PREFIX = "activation_token:";
    private final StringRedisTemplate redisTemplate;

    public RedisActivationTokenAdapter(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }

    @Override
    public void saveActivationToken(String username, String token, long ttlInMinutes){
        String key = KEY_PREFIX + token;
        redisTemplate.opsForValue().set(key, username, ttlInMinutes, TimeUnit.MINUTES);
    }

    @Override
    public Optional<String> getUsernameByActivationToken(String token){
        String key = KEY_PREFIX + token;
        String username = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(username);
    }

    @Override
    public void deleteActivationToken(String token){
        String key = KEY_PREFIX + token;
        redisTemplate.delete(key);
    }
}
