package com.sangboak.adminapp.scheduler;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log
@Service
public class BatchScheduler {

    @Scheduled(fixedDelay = 1000)
    public void scheduleTest() {
        log.warning("스케쥴러 발진");
    }
}
