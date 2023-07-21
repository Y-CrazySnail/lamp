package com.yeem.auth.service;

import com.yeem.auth.dto.WxLoginDTO;

public interface IZeroAuthService {
    String signupOrLogin(WxLoginDTO wxLoginDTO);
}
