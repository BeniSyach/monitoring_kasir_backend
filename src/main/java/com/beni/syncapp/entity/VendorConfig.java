package com.beni.syncapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VendorConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Vendor vendor;

    private String baseUrl;

    private String authType;

    @Column(columnDefinition = "TEXT")
    private String authValue;

    private String contentType;

    private Integer timeoutMs;
}