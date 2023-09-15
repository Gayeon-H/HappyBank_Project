package com.hgy.happybank.scheduler;

import com.hgy.happybank.record.domain.dto.RecordDTO;
import com.hgy.happybank.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataBatchJob {

    private final RedisTemplate<String, RecordDTO> redisTemplate;
    private final RecordRepository recordRepository;

    public void performBatchJob() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = LocalDate.now().minusDays(1).format(formatter);

        log.info("{} : 배치작업 시작", formattedDate);
        Set<String> keys = Objects.requireNonNull(redisTemplate.keys("record:" + formattedDate + ":*"));
        List<RecordDTO> dataFromRedis = Objects.requireNonNull(redisTemplate.opsForValue().multiGet(keys));

        if (dataFromRedis.size() > 0) {
            recordRepository.saveAll(dataFromRedis
                    .stream().map(RecordDTO::toRecord)
                    .collect(Collectors.toList()));
        }
        log.info("{} : 배치작업 종료", formattedDate);
    }
}
