package com.yeem.lamp.x.model;

import lombok.Data;

@Data
public class XResponse<T> {
    private Boolean success;
    private String mgr;
    private T obj;
}
