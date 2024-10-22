package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.CFUser;

public interface ICFUserService extends IService<CFUser> {
    CFUser login(CFUser user);
}
