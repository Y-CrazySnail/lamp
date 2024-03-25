package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneUser;

public interface IOneUserService extends IService<OneUser> {
    OneUser getByIdWithOther(Long id);

    OneUser getByWechatOpenId(String wechatOpenId);

    OneUser login(OneUser user);
}
