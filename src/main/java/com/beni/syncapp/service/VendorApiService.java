package com.beni.syncapp.service;

import com.beni.syncapp.dto.VendorApiResult;
import com.beni.syncapp.entity.VendorConfig;
import com.beni.syncapp.entity.VendorEndpoint;
import com.beni.syncapp.entity.VendorStoreMapping;
import com.beni.syncapp.repository.VendorConfigRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class VendorApiService {

    private final VendorConfigRepository configRepo;

    private final GenericHttpClient httpClient;

    private final AuthBuilder authBuilder;

    private final TemplateEngine templateEngine;

    public VendorApiService(
            VendorConfigRepository configRepo,
            GenericHttpClient httpClient,
            AuthBuilder authBuilder,
            TemplateEngine templateEngine
    ) {
        this.configRepo = configRepo;
        this.httpClient = httpClient;
        this.authBuilder = authBuilder;
        this.templateEngine = templateEngine;
    }

    public VendorApiResult call(
            VendorEndpoint endpoint,
            VendorStoreMapping mapping,
            LocalDate date
    ) {

        VendorConfig config = configRepo
                .findByVendor_Code(
                        mapping.getVendor().getCode()
                )
                .orElseThrow();

        Map<String, String> params =
                new HashMap<>();

        params.put(
                "branchId",
                mapping.getBranchId()
        );

        params.put(
                "date",
                date.toString()
        );

        String url =
                config.getBaseUrl()
                        + endpoint.getUrlPath();

        if (endpoint.getQueryTemplate() != null
                && !endpoint.getQueryTemplate().isBlank()) {

            String query =
                    templateEngine.render(
                            endpoint.getQueryTemplate(),
                            params
                    );

            url += "?" + query;
        }

        String body = null;

        if ("POST".equalsIgnoreCase(
                endpoint.getHttpMethod()
        )) {

            body =
                    templateEngine.render(
                            endpoint.getBodyTemplate(),
                            params
                    );
        }

        HttpHeaders headers =
                new HttpHeaders();

        authBuilder.apply(
                headers,
                config
        );

        HttpEntity<?> entity =
                new HttpEntity<>(
                        body,
                        headers
                );

        String response =
                httpClient.execute(
                        url,
                        endpoint.getHttpMethod(),
                        entity
                );

        VendorApiResult result =
                new VendorApiResult();

        result.setRequestPayload(
                body != null ? body : url
        );

        result.setResponsePayload(
                response
        );

        return result;
    }
}