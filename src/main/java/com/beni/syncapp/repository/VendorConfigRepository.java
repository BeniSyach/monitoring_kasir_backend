package com.beni.syncapp.repository;

import com.beni.syncapp.entity.VendorConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorConfigRepository extends JpaRepository<VendorConfig, Long> {

    Optional<VendorConfig> findByVendor_Code(String code);
}