package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Services;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MemberDTO {
    private Long id;
    private String email;
    private String wechat;
    private String remark;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateSubscription;
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
