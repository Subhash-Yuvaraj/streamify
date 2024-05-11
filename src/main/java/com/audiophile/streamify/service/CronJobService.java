package com.audiophile.streamify.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface CronJobService {
    @Scheduled(cron = "0 0 0 1 * ?")
    void updateSongPlayMetrics();
}
