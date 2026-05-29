package com.beni.syncapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private Store store;

    @Column(unique = true)
    private String sourceId;

    private String name;

    // raw original dari vendor
    private String activityDate;

    // datetime asli untuk query/filter
    private LocalDateTime transDate;

    @Column(
            name = "total_transaksi",
            precision = 18,
            scale = 2
    )
    private BigDecimal totalTransaksi;
}