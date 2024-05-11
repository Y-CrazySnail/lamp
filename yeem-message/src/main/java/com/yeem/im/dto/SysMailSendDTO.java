package com.yeem.im.dto;

public class SysMailSendDTO extends SysIMSendDTO {
    private String toEmail;
    private String attachment;
    private boolean htmlFlag;

    public boolean getHtmlFlag() {
        return htmlFlag;
    }

    public void setHtmlFlag(boolean htmlFlag) {
        this.htmlFlag = htmlFlag;
    }

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
