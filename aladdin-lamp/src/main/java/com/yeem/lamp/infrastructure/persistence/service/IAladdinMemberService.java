package com.yeem.lamp.infrastructure.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.infrastructure.persistence.entity.MemberDo;

public interface IAladdinMemberService extends IService<MemberDo> {
    MemberDo getByUUID(String uuid);
    IPage<MemberDo> pages(int current, int size, String email, String wechat);
}
