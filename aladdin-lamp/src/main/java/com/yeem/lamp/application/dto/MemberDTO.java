package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Services;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class MemberDTO {
    private Long id;
    private String wechat;
    private String email;
    private String password;
    private String referrerCode;
    private Integer level;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date endDate;
    private Integer bandwidth;
    private Integer period;
    private BigDecimal price;
    private String uuid;
    private List<Services> servicesList;

    public MemberDTO() {
    }

    public static MemberDTO init(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        BeanUtil.copyProperties(member, memberDTO);
        memberDTO.setBeginDate(member.getServices().getBeginDate());
        memberDTO.setEndDate(member.getServices().getEndDate());
        memberDTO.setBandwidth(member.getServices().getPlan().getBandwidth());
        memberDTO.setPeriod(member.getServices().getPlan().getPeriod());
        memberDTO.setPrice(member.getServices().getPlan().getPrice());
        memberDTO.setUuid(member.getServices().getUuid());
        return memberDTO;
    }

    public Member convertMember() {
        Member member = new Member();
        BeanUtil.copyProperties(this, member);
        return member;
    }
}
