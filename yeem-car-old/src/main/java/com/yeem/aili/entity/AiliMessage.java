package com.yeem.aili.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aili_message", autoResultMap = true)
public class AiliMessage extends BaseEntity {
    private String name;
    private String phone;
    private String email;
    private String content;
}