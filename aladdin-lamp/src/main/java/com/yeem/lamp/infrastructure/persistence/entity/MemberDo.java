package com.yeem.lamp.infrastructure.persistence.entity;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateSubscription;

    public Member convertMember() {
        Member member = new Member();
        member.setId(this.getId());
        member.setEmail(this.getEmail());
        member.setWechat(this.getWechat());
        member.setRemark(this.getRemark());
        member.setPassword(this.getPassword());
        member.setLastUpdateSubscription(this.getLastUpdateSubscription());
        return member;
    }
}
