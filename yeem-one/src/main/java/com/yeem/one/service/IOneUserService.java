package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneUser;

import java.io.Serializable;

public interface IOneUserService extends IService<OneUser> {
    OneUser getByIdWithAddress(Long id);
}
