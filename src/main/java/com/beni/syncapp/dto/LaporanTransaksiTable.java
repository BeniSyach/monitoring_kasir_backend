package com.beni.syncapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LaporanTransaksiTable {
    private Long storeId;

    private String npwpd;

    private String namaObjekPajak;

    private String wilayah;

    private Map<Integer, BigDecimal> transaksiHarian;
}
