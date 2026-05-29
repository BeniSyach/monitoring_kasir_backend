package com.beni.syncapp.controller;

import com.beni.syncapp.entity.VendorEndpoint;
import com.beni.syncapp.repository.VendorEndpointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-endpoints")
@RequiredArgsConstructor
public class VendorEndpointController {

    private final VendorEndpointRepository vendorEndpointRepository;

    @GetMapping
    public List<VendorEndpoint> getAll() {
        return vendorEndpointRepository.findAll();
    }

    @GetMapping("/{id}")
    public VendorEndpoint getById(@PathVariable Long id) {
        return vendorEndpointRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping
    public VendorEndpoint create(
            @RequestBody VendorEndpoint request
    ) {

        return vendorEndpointRepository.save(request);
    }

    @PutMapping("/{id}")
    public VendorEndpoint update(
            @PathVariable Long id,
            @RequestBody VendorEndpoint request
    ) {

        VendorEndpoint endpoint = vendorEndpointRepository.findById(id)
                .orElseThrow();

        endpoint.setVendor(request.getVendor());
        endpoint.setName(request.getName());
        endpoint.setUrlPath(request.getUrlPath());
        endpoint.setHttpMethod(request.getHttpMethod());
        endpoint.setQueryTemplate(request.getQueryTemplate());
        endpoint.setBodyTemplate(request.getBodyTemplate());

        return vendorEndpointRepository.save(endpoint);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        vendorEndpointRepository.deleteById(id);

        return "Vendor Endpoint berhasil dihapus";
    }
}