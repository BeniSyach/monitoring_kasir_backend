package com.beni.syncapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VendorEndpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    private String name;

    private String urlPath;

    private String httpMethod; // ✅ INI YANG BENAR

    @Column(columnDefinition = "TEXT")
    private String queryTemplate;

    @Column(columnDefinition = "TEXT")
    private String bodyTemplate;
}