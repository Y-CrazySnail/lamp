package com.yeem.im.dto;

public class SysSMSSendDTO extends SysIMSendDTO {
    private String phone;
    private String signName;
    private String sessionContext;
    /**
     * 短信码号扩展(可忽略)
     */
    private String extendCode;
    /**
     * 国内短信无需填写
     */
    private String senderId;

    public String getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSessionContext() {
        return sessionContext;
    }

    public void setSessionContext(String sessionContext) {
        this.sessionContext = sessionContext;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
