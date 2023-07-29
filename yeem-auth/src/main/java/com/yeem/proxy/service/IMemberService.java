package com.yeem.proxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.proxy.entity.Member;

public interface IMemberService extends IService<Member> {
    void resetMemberData();

    void calculateData();
}
