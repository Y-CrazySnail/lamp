package com.yeem.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_member")
public class LampMember extends BaseEntity {

    private String wechat; // 微信

    private String email; // 邮箱

    @TableField(exist = false)
    private String username; // 用户名

    private String password; // 密码

    private Integer level; // 会员等级

    private String referrerCode; // 推荐人

    private String referralCode; // 推荐码

    private String remark; // 备注

    private BigDecimal balance; // 余额

    @TableField(exist = false)
    private String token; // token
}
