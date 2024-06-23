package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_member", autoResultMap = true)
public class MemberDo extends BaseDo {
    private String email;
    private String wechat;
    private String remark;
    private String password;

    public static MemberDo init(Member member) {
        MemberDo memberDo = new MemberDo();
        BeanUtil.copyProperties(member, memberDo);
        return memberDo;
    }

    public Member convertMember() {
        Member member = new Member();
        BeanUtil.copyProperties(this, member);
        return member;
    }
}
