package com.beni.syncapp.repository;

import com.beni.syncapp.entity.VendorEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface VendorEndpointRepository extends JpaRepository<VendorEndpoint, Long> {

    Optional<VendorEndpoint> findByVendor_CodeAndName(String code, String name);
    List<VendorEndpoint> findByVendor_Code(
            String vendorCode
    );
}