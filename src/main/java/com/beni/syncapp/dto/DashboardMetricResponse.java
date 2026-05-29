package com.beni.syncapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class DashboardMetricResponse {

    private BigDecimal dasarPengenaanPajak;

    private BigDecimal pajak;

    private BigDecimal totalTransaksi;
}