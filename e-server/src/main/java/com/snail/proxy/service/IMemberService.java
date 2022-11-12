package com.snail.proxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.proxy.entity.Member;

public interface IMemberService extends IService<Member> {
    void resetMemberData();
}
