package com.yeem.lamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinService;

import java.util.List;

public interface IAladdinServiceService extends IService<AladdinService> {
    List<AladdinService> listByMemberId(Long memberId);
}
