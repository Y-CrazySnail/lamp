package com.snail.auth.service;

import com.snail.auth.dto.WxLoginDTO;
import com.snail.zero.entity.UserExtra;

public interface IZeroAuthService {
    String signupOrLogin(WxLoginDTO wxLoginDTO);
}
