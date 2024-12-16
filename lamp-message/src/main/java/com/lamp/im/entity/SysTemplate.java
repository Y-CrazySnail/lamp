package com.lamp.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_template")
public class SysTemplate extends BaseEntity {
    private String name;
    private String type;
    private String subject;
    private String content;
    private boolean mailHtmlFlag;
    private String smsTemplateId;
    private String smsSignName;
}

