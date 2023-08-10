package com.yeem.car_film_saas.dto;

import java.util.Map;

public class SysIMSendDTO {
    private String templateName;
    private String templateType;
    private String businessId;
    private Map<String, Object> replaceMap;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Map<String, Object> getReplaceMap() {
        return replaceMap;
    }

    public void setReplaceMap(Map<String, Object> replaceMap) {
        this.replaceMap = replaceMap;
    }
}
