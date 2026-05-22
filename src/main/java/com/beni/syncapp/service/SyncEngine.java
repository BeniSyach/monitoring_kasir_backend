package com.beni.syncapp.service;

import com.beni.syncapp.dto.VendorApiResult;
import com.beni.syncapp.entity.RawRequestLog;
import com.beni.syncapp.entity.VendorEndpoint;
import com.beni.syncapp.entity.VendorStoreMapping;
import com.beni.syncapp.repository.VendorEndpointRepository;
import com.beni.syncapp.repository.VendorStoreMappingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.util.List;

@Service
public class SyncEngine {

    private final VendorStoreMappingRepository mappingRepo;

    private final VendorEndpointRepository endpointRepo;

    private final VendorApiService vendorApiService;

    private final ActivityService activityService;

    private final RawRequestLogService logService;

    public SyncEngine(
            VendorStoreMappingRepository mappingRepo,
            VendorEndpointRepository endpointRepo,
            VendorApiService vendorApiService,
            ActivityService activityService,
            RawRequestLogService logService
    ) {
        this.mappingRepo = mappingRepo;
        this.endpointRepo = endpointRepo;
        this.vendorApiService = vendorApiService;
        this.activityService = activityService;
        this.logService = logService;
    }

    public void sync(String vendorCode) {

        List<VendorStoreMapping> mappings =
                mappingRepo.findByVendor_Code(
                        vendorCode
                );

        List<VendorEndpoint> endpoints =
                endpointRepo.findByVendor_Code(
                        vendorCode
                );

        LocalDate startDate =
                LocalDate.of(2026, 1, 1);

        LocalDate endDate =
                LocalDate.now();

        while (!startDate.isAfter(endDate)) {

            for (VendorEndpoint endpoint : endpoints) {

                for (VendorStoreMapping mapping : mappings) {

                    processStore(
                            endpoint,
                            mapping,
                            startDate
                    );
                }
            }

            startDate = startDate.plusDays(1);
        }
    }

    private void processStore(
            VendorEndpoint endpoint,
            VendorStoreMapping mapping,
              LocalDate date
    ) {

        RawRequestLog log =
                logService.create(
                        mapping.getVendor().getCode(),
                        endpoint.getUrlPath()
                );

        try {

            VendorApiResult result =
                    vendorApiService.call(
                            endpoint,
                            mapping,
                            date
                    );

            logService.success(
                    log,
                    result
            );

            activityService.process(
                    mapping,
                    result
            );

        } catch (Exception e) {

            logService.failed(log, e);
        }
    }
}