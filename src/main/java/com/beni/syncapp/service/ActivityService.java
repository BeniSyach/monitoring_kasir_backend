package com.beni.syncapp.service;

import com.beni.syncapp.dto.VendorApiResult;
import com.beni.syncapp.entity.Activity;
import com.beni.syncapp.entity.VendorStoreMapping;
import com.beni.syncapp.repository.ActivityRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                root.path("data");

        if (!dataNode.isArray()) {

            System.out.println(
                    "Tidak ada data activity"
            );

            return;
        }

        // format datetime vendor
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        for (JsonNode item : dataNode) {

            String sourceId =
                    item.path("no_transaksi")
                            .asText()
                            .trim();

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
                    item.path("nmhhb")
                            .asText("")
                            .trim()
            );

            // =========================
            // TRANS DATE
            // =========================
            String transDateString =
                    item.path("trans_date")
                            .asText("")
                            .trim();

            // simpan raw string
            activity.setActivityDate(
                    transDateString
            );

            // parse ke LocalDateTime
            if (!transDateString.isEmpty()) {

                LocalDateTime transDate =
                        LocalDateTime.parse(
                                transDateString,
                                formatter
                        );

                activity.setTransDate(
                        transDate
                );
            }

            // =========================
            // TOTAL TRANSAKSI
            // =========================
            BigDecimal totalTransaksi =
                    BigDecimal.valueOf(
                            item.path("sub_total")
                                    .asLong(0)
                    );

            activity.setTotalTransaksi(
                    totalTransaksi
            );

            activityRepo.save(activity);
        }
    }
}