package com.yeem.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.auth.entity.ThirdPartyLogin;

public interface IThirdPartyLoginService extends IService<ThirdPartyLogin> {
    ThirdPartyLogin getByPhone(String application, String phone);
    ThirdPartyLogin getByWechatMiniProgram(String application, String openId);
    boolean saveByWechatMiniProgram(Long userId, String application, String openId);
}
