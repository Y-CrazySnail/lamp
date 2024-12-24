package com.lamp.infrastructure.xui.entity;

import lombok.Data;

@Data
public class XuiResponse<T> {
    private boolean success;
    private String msg;
    private T obj;
}
