package com.beni.syncapp.service;

import com.beni.syncapp.dto.VendorApiResult;
import com.beni.syncapp.entity.Activity;
import com.beni.syncapp.entity.VendorStoreMapping;
import com.beni.syncapp.repository.ActivityRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private final ActivityRepository activityRepo;

    private final ObjectMapper mapper =
            new ObjectMapper();

    public ActivityService(
            ActivityRepository activityRepo
    ) {
        this.activityRepo = activityRepo;
    }

    public void process(
            VendorStoreMapping mapping,
            VendorApiResult result
    ) throws Exception {

        JsonNode root =
                mapper.readTree(
                        result.getResponsePayload()
                );

        JsonNode dataNode =
                root.get("data");

        if (dataNode == null || !dataNode.isArray()) {

            System.out.println(
                    "Tidak ada data activity"
            );

            return;
        }

        for (JsonNode item : dataNode) {

            String sourceId =
                    item.path("no_transaksi").asText();

            // skip jika sudah ada
            if (activityRepo.existsBySourceId(sourceId)) {
                continue;
            }

            Activity activity =
                    new Activity();

            activity.setVendor(
                    mapping.getVendor()
            );

            activity.setStore(
                    mapping.getStore()
            );

            activity.setSourceId(
                    sourceId
            );

            activity.setName(
                    item.path("nmhhb").asText().trim()
            );

            activity.setActivityDate(
                    item.path("trans_date").asText()
            );

            activityRepo.save(activity);
        }
    }
}