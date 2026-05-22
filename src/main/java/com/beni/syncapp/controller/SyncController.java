package com.beni.syncapp.controller;

import com.beni.syncapp.service.SyncEngine;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sync")
public class SyncController {

    private final SyncEngine syncEngine;

    public SyncController(
            SyncEngine syncEngine
    ) {
        this.syncEngine = syncEngine;
    }

    @PostMapping("/{vendor}")
    public String sync(
            @PathVariable String vendor
    ) {

        syncEngine.sync(vendor);

        return "SUCCESS";
    }
}