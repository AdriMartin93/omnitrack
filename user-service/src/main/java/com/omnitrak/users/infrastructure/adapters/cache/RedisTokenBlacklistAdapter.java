package com.omnitrak.users.infrastructure.adapters.cache;

import com.omnitrak.users.domain.ports.out.auth.TokenBlacklistPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
public class RedisTokenBlacklistAdapter implements TokenBlacklistPort {

    private static final String BLACKLIST_PREFIX = "blacklist:token:";
    private static final String USER_REVOKE_PREFIX = "blacklist:user:";

    private static final long DEFAULT_BLACKLIST_TTL_HOURS = 24*7;

    private final StringRedisTemplate redisTemplate;

    public RedisTokenBlacklistAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void revokeToken(String token) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "revoked", DEFAULT_BLACKLIST_TTL_HOURS, TimeUnit.HOURS);
    }

    @Override
    public void revokeAllUserTokens(UUID userId){
        String key = USER_REVOKE_PREFIX + userId.toString();
        String currentTimestamp = String.valueOf(System.currentTimeMillis());
        redisTemplate.opsForValue().set(key, currentTimestamp, DEFAULT_BLACKLIST_TTL_HOURS, TimeUnit.HOURS);
    }

    @Override
    public boolean isTokenRevoked(String token) {
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }





}
