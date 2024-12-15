package com.yeem.lamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.LampMember;

public interface LampMemberService extends IService<LampMember> {

    LampMember login(LampMember member);

}
