package com.beni.syncapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreStatsResponse {

    private long totalStore;

    private long storeAktif;

    private long storeTidakAktif;

    private long offline;

    private long tutup;
}