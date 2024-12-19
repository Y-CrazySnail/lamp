package com.lamp.xui.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.xui.entity.XuiInbound;
import com.lamp.xui.entity.XuiVmessSettings;

import java.io.IOException;

public class VmessSettingParser implements SettingParser {
    @Override
    public void parse(XuiInbound xuiInbound) {
        ObjectMapper objectMapper = new ObjectMapper();
        String settings = xuiInbound.getSettings();
        try {
            XuiVmessSettings readValue = objectMapper.readValue(settings, XuiVmessSettings.class);
            xuiInbound.setSettingsObject(readValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
