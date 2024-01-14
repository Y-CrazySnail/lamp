package com.yeem.lamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.entity.Member;

public interface IAladdinMemberService extends IService<AladdinMember> {
    AladdinMember getByUUID(String uuid);
}
