package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "car_film_user")
public class CFUser extends BaseCF {
    /**
     * 产品代码
     */
    private String productNo;
    /**
     * OpenID
     */
    private String openId;
    /**
     * Code
     */
    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String token;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 录入质保权限
     */
    private String qualityPermission;
    /**
     * 在保数量
     */
    @TableField(exist = false)
    private Integer normalQualityNumber;
    /**
     * 过期数量
     */
    @TableField(exist = false)
    private Integer expiredQualityNumber;
}
