package com.yeem.lamp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.entity.Member;

import java.io.Serializable;

public interface IAladdinMemberService extends IService<AladdinMember> {
    AladdinMember getByUUID(String uuid);
    IPage<AladdinMember> pages(int current, int size, String email, String wechat);
}
