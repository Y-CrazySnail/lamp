package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_user", autoResultMap = true)
@Data
public class OneUser extends BaseEntity {
    private Long tenantId;
    private String wechatOpenId;
    private String nickName;
    private String userAvatar;
    private String userPhone;
    @TableField(exist = false)
    private String wechatAppId;
    @TableField(exist = false)
    private String wechatCode;
    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private Integer userAddressCount;
    @TableField(exist = false)
    private Integer userOrderCount;
    @TableField(exist = false)
    private List<OneAddress> addressList;
    @TableField(exist = false)
    private List<OneCart> cartList;
}