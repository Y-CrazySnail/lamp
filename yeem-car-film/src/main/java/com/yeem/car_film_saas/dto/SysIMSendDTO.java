package com.yeem.car_film_saas.dto;

import java.util.Date;
import java.util.Map;

public class SysIMSendDTO {
    private String templateName;
    private String templateType;
    private String businessId;
    private Date timingTime;
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

    public Date getTimingTime() {
        return timingTime;
    }

    public void setTimingTime(Date timingTime) {
        this.timingTime = timingTime;
    }

    public Map<String, Object> getReplaceMap() {
        return replaceMap;
    }

    public void setReplaceMap(Map<String, Object> replaceMap) {
        this.replaceMap = replaceMap;
    }
}
