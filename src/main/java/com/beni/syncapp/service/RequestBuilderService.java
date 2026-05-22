package com.beni.syncapp.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RequestBuilderService {

    private final TemplateEngine templateEngine;

    public RequestBuilderService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    // 🔥 GENERIC VERSION (TIDAK KUNCI KE branchId/date SAJA)
    public String build(String template, Map<String, String> params) {

        if (template == null || template.isEmpty()) {
            return null;
        }

        return templateEngine.render(template, params);
    }

    // 🔥 OPTIONAL HELPER (BIAR MUDAH DIPAKAI)
    public Map<String, String> defaultParams(String branchId, String date) {

        Map<String, String> params = new HashMap<>();
        params.put("branchId", branchId);
        params.put("date", date);

        return params;
    }
}