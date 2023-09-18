package com.hgy.happybank.record.service;

import com.hgy.happybank.record.domain.dto.RecordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisDataService {

    private final RedisTemplate<String, RecordDTO> redisTemplate;

    public void addToRedis(String key, RecordDTO value) {
        redisTemplate.opsForValue().set(key, value, 1, TimeUnit.DAYS);
    }

    public boolean isExist(String key) {
        return redisTemplate.opsForValue().get(key) != null;
    }
}
