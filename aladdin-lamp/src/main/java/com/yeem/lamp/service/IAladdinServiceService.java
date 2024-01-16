package com.yeem.lamp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.entity.AladdinService;

import java.io.Serializable;
import java.util.List;

public interface IAladdinServiceService extends IService<AladdinService> {
    List<AladdinService> listByMemberId(Long memberId);
    boolean removeByMemberId(Serializable id);
    IPage<AladdinService> pages(int current, int size, Long memberId, String status);
}
