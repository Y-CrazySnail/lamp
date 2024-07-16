package com.yeem.lamp.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private Long id;
    private String wechat;
    private String email;
    private String password;
    private String referralCode;
    private String referrerCode;
    private Integer level;
    private String remark;
    private LocalDateTime createTime;
    private Services services;
}
