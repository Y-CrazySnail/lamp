package com.yeem.lamp.infrastructure.x.model;

import lombok.Data;

@Data
public class XResponse<T> {
    private boolean success;
    private String msg;
    private T obj;
}
