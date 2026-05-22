package com.beni.syncapp.repository;

import com.beni.syncapp.entity.VendorStoreMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorStoreMappingRepository
        extends JpaRepository<VendorStoreMapping, Long> {

    List<VendorStoreMapping>
    findByVendor_Code(String vendorCode);
}