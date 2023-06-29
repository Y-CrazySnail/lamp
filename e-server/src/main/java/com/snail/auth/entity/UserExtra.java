package com.snail.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user_extra", autoResultMap = true)
public class UserExtra extends BaseEntity{
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像链接
     */
    private String avatarUrl;
    /**
     * 性别 0未知 1男 2女
     */
    private Integer gender;
}
