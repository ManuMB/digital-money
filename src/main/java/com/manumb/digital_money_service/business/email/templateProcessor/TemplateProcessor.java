package com.manumb.digital_money_service.business.email.templateProcessor;

import java.util.Map;

public class TemplateProcessor {
    public static String processTemplate(String template, Map<String, String> variables) {
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }
}
