package com.yeem.lamp.infrastructure.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.infrastructure.persistence.entity.MemberEntity;

public interface IAladdinMemberService extends IService<MemberEntity> {
    MemberEntity getByUUID(String uuid);
    IPage<MemberEntity> pages(int current, int size, String email, String wechat);
}
