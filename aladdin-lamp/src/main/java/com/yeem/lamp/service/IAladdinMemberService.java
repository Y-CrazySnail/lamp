package com.yeem.lamp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinMemberEntity;

public interface IAladdinMemberService extends IService<AladdinMemberEntity> {
    AladdinMemberEntity getByUUID(String uuid);
    IPage<AladdinMemberEntity> pages(int current, int size, String email, String wechat);
}
