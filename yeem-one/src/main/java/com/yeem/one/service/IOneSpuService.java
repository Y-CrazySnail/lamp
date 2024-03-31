package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneSku;
import com.yeem.one.entity.OneSpu;

import java.util.List;

public interface IOneSpuService extends IService<OneSpu> {
    List<OneSpu> list(OneSpu spu);
    OneSpu getWithOther(Long id, Long tenantId);
}
