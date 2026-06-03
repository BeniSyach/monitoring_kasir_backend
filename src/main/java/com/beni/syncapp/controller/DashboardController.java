package com.beni.syncapp.controller;

import com.beni.syncapp.dto.*;
import com.beni.syncapp.repository.ActivityRepository;
import com.beni.syncapp.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final ActivityRepository activityRepository;
    private final DashboardService service;

    // =========================
    // TOP TRANSAKSI
    // =========================
    @GetMapping("/top-transaksi")
    public List<TopStoreResponse> topTransaksi() {

        return activityRepository
                .getTopTransactionStores()
                .stream()
                .map(obj -> new TopStoreResponse(
                        String.valueOf(obj[0]),
                        (BigDecimal) obj[1]
                ))
                .toList();
    }

    // =========================
    // TOP PAJAK
    // =========================
    @GetMapping("/top-pajak")
    public List<TopStoreResponse> topPajak() {

        return activityRepository
                .getTopTaxStores()
                .stream()
                .map(obj -> new TopStoreResponse(
                        String.valueOf(obj[0]),
                        (BigDecimal) obj[1]
                ))
                .toList();
    }

    // =========================
    // 100 TRANSAKSI TERAKHIR
    // =========================
    @GetMapping("/last-transactions")
    public List<LastTransactionResponse> lastTransactions() {

        return activityRepository
                .getLastTransactions()
                .stream()
                .map(obj -> new LastTransactionResponse(
                        String.valueOf(obj[0]),
                        (BigDecimal) obj[1],
                        (BigDecimal) obj[2]
                ))
                .toList();
    }

    @GetMapping("/metrics")
    public DashboardMetricResponse getMetrics(

            @RequestParam String startDate,

            @RequestParam String endDate
    ) {

        LocalDateTime start =
                LocalDate.parse(startDate)
                        .atStartOfDay();

        LocalDateTime end =
                LocalDate.parse(endDate)
                        .atTime(23, 59, 59);

        BigDecimal totalTransaksi =
                activityRepository
                        .getTotalTransaksi(
                                start,
                                end
                        );

        BigDecimal pajak =
                totalTransaksi.multiply(
                        new BigDecimal("0.10")
                );

        BigDecimal dpp =
                totalTransaksi.subtract(
                        pajak
                );

        return new DashboardMetricResponse(
                dpp,
                pajak,
                totalTransaksi
        );
    }

    @GetMapping("/chart")
    public ChartResponse getChart(

            @RequestParam String startDate,

            @RequestParam String endDate,

            @RequestParam(defaultValue = "harian")
            String type
    ) {

        LocalDateTime start =
                LocalDate.parse(startDate)
                        .atStartOfDay();

        LocalDateTime end =
                LocalDate.parse(endDate)
                        .atTime(23,59,59);

        List<Object[]> results;

        if (type.equalsIgnoreCase("bulanan")) {

            results =
                    activityRepository
                            .getMonthlyChart(
                                    start,
                                    end
                            );

        } else {

            results =
                    activityRepository
                            .getDailyChart(
                                    start,
                                    end
                            );
        }

        List<String> categories =
                new ArrayList<>();

        List<Long> data =
                new ArrayList<>();

        for (Object[] row : results) {

            categories.add(
                    row[0].toString()
            );

            data.add(
                    ((Number) row[1]).longValue()
            );
        }

        return new ChartResponse(
                categories,
                data
        );
    }

    @GetMapping("/objek-pajak")
    public Map<String, Object> get(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return (Map<String, Object>) service.getData(
                startDate,
                endDate,
                page,
                size
        );
    }


}