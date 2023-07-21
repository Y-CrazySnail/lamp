package com.snail.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.zero.entity.ZeroAddress;

import java.util.List;

public interface IZeroAddressService extends IService<ZeroAddress> {
    List<ZeroAddress> listByUsername(String username);
    boolean save(ZeroAddress zeroAddress);
    boolean update(ZeroAddress zeroAddress);
    boolean removeById(ZeroAddress zeroAddress);
}
