package com.yeem.lamp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinService;

import java.io.Serializable;
import java.util.List;

public interface IAladdinServiceService extends IService<AladdinService> {
    List<AladdinService> listByMemberId(Long memberId);

    List<AladdinService> listValidByMemberId(Long memberId);

    boolean removeByMemberId(Serializable id);

    public IPage<AladdinService> pages(int current, int size,
                                       Long memberId,
                                       String status,
                                       String wechat,
                                       String email);

    AladdinService getByUUID(String uuid);
}
