package com.beni.syncapp.controller;

import com.beni.syncapp.entity.Vendor;
import com.beni.syncapp.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorRepository vendorRepository;

    @GetMapping
    public List<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Vendor getById(@PathVariable Long id) {
        return vendorRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping
    public Vendor create(@RequestBody Vendor request) {
        return vendorRepository.save(request);
    }

    @PutMapping("/{id}")
    public Vendor update(
            @PathVariable Long id,
            @RequestBody Vendor request
    ) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow();

        vendor.setCode(request.getCode());
        vendor.setName(request.getName());
        vendor.setActive(request.isActive());

        return vendorRepository.save(vendor);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        vendorRepository.deleteById(id);

        return "Vendor berhasil dihapus";
    }
}