package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneSpu;

import java.util.List;

public interface IOneSpuService extends IService<OneSpu> {
    List<OneSpu> listForWechat(OneSpu spu);
}
