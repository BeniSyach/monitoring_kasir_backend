package com.beni.syncapp.controller;

import com.beni.syncapp.entity.VendorConfig;
import com.beni.syncapp.repository.VendorConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-configs")
@RequiredArgsConstructor
public class VendorConfigController {

    private final VendorConfigRepository vendorConfigRepository;

    @GetMapping
    public List<VendorConfig> getAll() {
        return vendorConfigRepository.findAll();
    }

    @GetMapping("/{id}")
    public VendorConfig getById(@PathVariable Long id) {
        return vendorConfigRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping
    public VendorConfig create(
            @RequestBody VendorConfig request
    ) {

        return vendorConfigRepository.save(request);
    }

    @PutMapping("/{id}")
    public VendorConfig update(
            @PathVariable Long id,
            @RequestBody VendorConfig request
    ) {

        VendorConfig config = vendorConfigRepository.findById(id)
                .orElseThrow();

        config.setVendor(request.getVendor());
        config.setBaseUrl(request.getBaseUrl());
        config.setAuthType(request.getAuthType());
        config.setAuthValue(request.getAuthValue());
        config.setContentType(request.getContentType());
        config.setTimeoutMs(request.getTimeoutMs());

        return vendorConfigRepository.save(config);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        vendorConfigRepository.deleteById(id);

        return "Vendor Config berhasil dihapus";
    }
}