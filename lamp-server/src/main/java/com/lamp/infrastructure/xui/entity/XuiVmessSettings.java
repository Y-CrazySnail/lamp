package com.lamp.infrastructure.xui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class XuiVmessSettings extends XuiSettings {
    private List<XuiVmessSetting> clients;
}
