package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroCart;

import java.util.List;

public interface IZeroCartService extends IService<ZeroCart> {
    List<ZeroCart> listByUsername();
    boolean remove(ZeroCart zeroCart);
}
