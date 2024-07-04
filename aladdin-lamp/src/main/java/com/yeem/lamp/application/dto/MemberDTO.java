package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Services;
import lombok.Data;

import java.util.List;

@Data
public class MemberDTO {
    private Long id;
    private String wechat;
    private String email;
    private String password;
    private String referralCode;
    private String referrerCode;
    private Integer level;
    private String remark;
    private List<Services> servicesList;

    public MemberDTO() {
    }

    public static MemberDTO init(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        BeanUtil.copyProperties(member, memberDTO);
        return memberDTO;
    }

    public Member convertMember() {
        Member member = new Member();
        BeanUtil.copyProperties(this, member);
        return member;
    }
}
