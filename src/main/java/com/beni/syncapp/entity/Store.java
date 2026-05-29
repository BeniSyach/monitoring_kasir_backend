package com.beni.syncapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String alias;

    // ACTIVE, INACTIVE
    private String status;

    // ONLINE, OFFLINE, TUTUP
    private String operationalStatus;

    private Double latitude;

    private Double longitude;

    // waktu terakhir sync
    private LocalDateTime lastSyncAt;
}