package com.beni.syncapp.controller;

import com.beni.syncapp.entity.VendorStoreMapping;
import com.beni.syncapp.repository.VendorStoreMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-store-mappings")
@RequiredArgsConstructor
public class VendorStoreMappingController {

    private final VendorStoreMappingRepository vendorStoreMappingRepository;

    @GetMapping
    public List<VendorStoreMapping> getAll() {
        return vendorStoreMappingRepository.findAll();
    }

    @GetMapping("/{id}")
    public VendorStoreMapping getById(@PathVariable Long id) {
        return vendorStoreMappingRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping
    public VendorStoreMapping create(
            @RequestBody VendorStoreMapping request
    ) {

        return vendorStoreMappingRepository.save(request);
    }

    @PutMapping("/{id}")
    public VendorStoreMapping update(
            @PathVariable Long id,
            @RequestBody VendorStoreMapping request
    ) {

        VendorStoreMapping mapping = vendorStoreMappingRepository.findById(id)
                .orElseThrow();

        mapping.setVendor(request.getVendor());
        mapping.setBranchId(request.getBranchId());
        mapping.setStore(request.getStore());

        return vendorStoreMappingRepository.save(mapping);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        vendorStoreMappingRepository.deleteById(id);

        return "Vendor Store Mapping berhasil dihapus";
    }
}