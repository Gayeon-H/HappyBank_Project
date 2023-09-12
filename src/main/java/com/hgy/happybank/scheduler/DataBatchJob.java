package com.hgy.happybank.scheduler;

import com.hgy.happybank.record.domain.dto.RecordDTO;
import com.hgy.happybank.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataBatchJob {

    private final RedisTemplate<String, RecordDTO> redisTemplate;
    private final RecordRepository recordRepository;

    public void performBatchJob() {
        for (String key : redisTemplate.keys("record:*")) {
            try {
                RecordDTO dto = redisTemplate.opsForValue().get(key);
                if (dto != null) {
                    redisTemplate.delete(key);
                    recordRepository.save(dto.toRecord());
                }
            } catch (Exception e) {
                log.info(e.getMessage() + ": DataBatchJob.performBatchJob()");
            }
        }
    }
}
