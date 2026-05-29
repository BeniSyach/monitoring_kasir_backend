package com.beni.syncapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TopStoreResponse {

    private String name;

    private BigDecimal total;
}