package com.snail.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.zero.entity.UserExtra;

public interface IUserExtraService extends IService<UserExtra> {
    UserExtra info(String username);
}
