package com.beni.syncapp.service;

import com.beni.syncapp.entity.RawRequestLog;
import com.beni.syncapp.entity.Vendor;
import com.beni.syncapp.repository.RawRequestLogRepository;
import com.beni.syncapp.repository.VendorRepository;
import com.beni.syncapp.dto.VendorApiResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RawRequestLogService {

    private final RawRequestLogRepository logRepo;
    private final VendorRepository vendorRepo;

    public RawRequestLogService(
            RawRequestLogRepository logRepo,
            VendorRepository vendorRepo
    ) {
        this.logRepo = logRepo;
        this.vendorRepo = vendorRepo;
    }

    public RawRequestLog create(
            String vendorCode,
            String endpoint
    ) {

        Vendor vendor = vendorRepo
                .findByCode(vendorCode)
                .orElseThrow();

        RawRequestLog log = new RawRequestLog();

        log.setVendor(vendor);
        log.setEndpointName(endpoint);
        log.setStatus("PROCESS");
        log.setRetryCount(0);
        log.setCreatedAt(LocalDateTime.now());

        return logRepo.save(log);
    }

    public void success(
            RawRequestLog log,
            VendorApiResult result
    ) {

        log.setStatus("SUCCESS");

        log.setRequestPayload(
                result.getRequestPayload()
        );

        log.setResponsePayload(
                result.getResponsePayload()
        );

        logRepo.save(log);
    }

    public void failed(
            RawRequestLog log,
            Exception e
    ) {

        log.setStatus("FAILED");
        log.setErrorMessage(e.getMessage());

        logRepo.save(log);
    }
}