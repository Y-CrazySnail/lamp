package com.snail.auth.service;

import com.snail.auth.dto.WxLoginDTO;

public interface IZeroAuthService {
    void signupOrLogin(WxLoginDTO wxLoginDTO);
}
