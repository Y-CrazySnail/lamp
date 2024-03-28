package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneSku;

import java.util.List;

public interface IOneSkuService extends IService<OneSku> {
    List<OneSku> listBySpuId(Long spuId);
}
