package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroUserExtra;

public interface IZeroUserExtraService extends IService<ZeroUserExtra> {
    ZeroUserExtra get(String username);
    ZeroUserExtra update(ZeroUserExtra zeroUserExtra);
}
