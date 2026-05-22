package com.beni.syncapp.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TemplateEngine {
    public String render(String template, Map<String, String> params) {

        String result = template;

        for (var entry : params.entrySet()) {
            result = result.replace(
                    "{{" + entry.getKey() + "}}",
                    entry.getValue()
            );
        }

        return result;
    }
}
