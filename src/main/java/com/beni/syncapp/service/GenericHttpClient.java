package com.beni.syncapp.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenericHttpClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public String execute(
            String url,
            String method,
            HttpEntity<?> entity
    ) {

        return restTemplate.exchange(
                url,
                HttpMethod.valueOf(method),
                entity,
                String.class
        ).getBody();
    }
}
