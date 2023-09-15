package com.hgy.happybank.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataBatchScheduler {

    private final DataBatchJob dataBatchJob;

    @Scheduled(cron = "0 59 23 * * *")
    public void executeBatchJob() {
        dataBatchJob.performBatchJob();
    }
}
