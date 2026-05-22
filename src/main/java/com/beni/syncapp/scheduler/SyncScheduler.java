package com.beni.syncapp.scheduler;

import com.beni.syncapp.service.SyncEngine;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncScheduler {

    private final SyncEngine syncEngine;

    public SyncScheduler(
            SyncEngine syncEngine
    ) {
        this.syncEngine = syncEngine;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void syncPajak() {

        syncEngine.sync("PAJAK");
    }
}