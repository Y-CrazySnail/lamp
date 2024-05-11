package com.yeem.im.dto;

public class SysTelegramSendDTO extends SysIMSendDTO {
    private String botId;
    private String toChatId;

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public String getToChatId() {
        return toChatId;
    }

    public void setToChatId(String toChatId) {
        this.toChatId = toChatId;
    }
}
