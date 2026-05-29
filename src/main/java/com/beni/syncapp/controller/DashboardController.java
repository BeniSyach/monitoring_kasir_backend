package com.beni.syncapp.controller;

import com.beni.syncapp.dto.LastTransactionResponse;
import com.beni.syncapp.dto.TopStoreResponse;
import com.beni.syncapp.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final ActivityRepository activityRepository;

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
}