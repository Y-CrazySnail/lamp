package com.snail.auth.service;

import com.snail.auth.dto.WxLoginDTO;
import com.snail.auth.entity.UserExtra;

public interface IZeroAuthService {
    String signupOrLogin(WxLoginDTO wxLoginDTO);

    UserExtra info(String username);
}
