package com.beni.syncapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ObjekPajakDTO {
    private String npwpd;
    private String nama;
    private String tipe;
    private String wilayah;
    private String perangkat;
    private String trxTerakhir;
    private BigDecimal total;
    private String status;
}