package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroCart;

import java.util.List;

public interface IZeroCartService extends IService<ZeroCart> {
    List<ZeroCart> listByUsername();
    List<ZeroCart> listByIdList(List<Long> cartIdList);
    boolean remove(ZeroCart zeroCart);
}
