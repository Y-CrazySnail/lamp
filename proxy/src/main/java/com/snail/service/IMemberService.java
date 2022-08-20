package com.snail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.entity.Member;

public interface IMemberService extends IService<Member> {
    void resetMemberData();
}
