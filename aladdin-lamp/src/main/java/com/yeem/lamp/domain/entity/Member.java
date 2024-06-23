package com.yeem.lamp.domain.entity;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String email;
    private String wechat;
    private String remark;
    private String password;
}
