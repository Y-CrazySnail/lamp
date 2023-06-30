package com.snail.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.zero.entity.ZeroUserExtra;

public interface IZeroUserExtraService extends IService<ZeroUserExtra> {
    ZeroUserExtra get(String username);
    ZeroUserExtra update(ZeroUserExtra zeroUserExtra);
}
