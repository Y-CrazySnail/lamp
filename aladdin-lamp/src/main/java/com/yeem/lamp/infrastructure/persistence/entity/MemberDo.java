package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.Plan;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

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
    @TableField(exist = false)
    private Date beginDate;
    @TableField(exist = false)
    private Date endDate;
    @TableField(exist = false)
    private Integer bandwidth;
    @TableField(exist = false)
    private Integer period;
    @TableField(exist = false)
    private BigDecimal price;
    @TableField(exist = false)
    private String uuid;

    public static MemberDo init(Member member) {
        MemberDo memberDo = new MemberDo();
        BeanUtil.copyProperties(member, memberDo);
        return memberDo;
    }

    public Member convertMember() {
        Member member = new Member();
        BeanUtil.copyProperties(this, member);
        Services services = new Services();
        services.setBeginDate(this.beginDate);
        services.setEndDate(this.endDate);
        services.setUuid(this.uuid);
        Plan plan = new Plan();
        plan.setBandwidth(this.bandwidth);
        plan.setPrice(this.price);
        plan.setPeriod(this.period);
        services.setPlan(plan);
        member.setServices(services);
        return member;
    }
}
