package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.entity.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_member", autoResultMap = true)
public class MemberDo extends BaseDo {
    private String wechat;
    private String email;
    private String password;
    private String referralCode;
    private String referrerCode;
    private Integer level;
    private String remark;

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
