package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneAddress;

import java.util.List;

public interface IOneAddressService extends IService<OneAddress> {
    List<OneAddress> listByUserId(Long userId);
}
