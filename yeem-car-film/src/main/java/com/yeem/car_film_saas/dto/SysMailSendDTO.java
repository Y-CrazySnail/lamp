package com.yeem.car_film_saas.dto;

public class SysMailSendDTO extends SysIMSendDTO {
    private String toEmail;
    private String attachment;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
