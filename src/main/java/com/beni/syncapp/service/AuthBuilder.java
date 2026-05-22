package com.beni.syncapp.service;

import com.beni.syncapp.entity.VendorConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class AuthBuilder {

    public void apply(HttpHeaders headers, VendorConfig config) {

        if (config == null || config.getAuthType() == null) {
            throw new IllegalArgumentException("Auth config tidak valid");
        }

        switch (config.getAuthType().toUpperCase()) {

            case "BASIC":
                headers.set(
                        "Authorization",
                        "Basic " + config.getAuthValue()
                );
                break;

            case "BEARER":
                headers.set(
                        "Authorization",
                        "Bearer " + config.getAuthValue()
                );
                break;

            case "APIKEY":
                headers.set(
                        "x-api-key",
                        config.getAuthValue()
                );
                break;

            default:
                throw new IllegalArgumentException(
                        "Auth type tidak didukung: " + config.getAuthType()
                );
        }
    }
}