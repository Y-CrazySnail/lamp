package com.yeem.lamp.presentation.request;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private String username;
    private String password;
}
