package com.yeem.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
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
    private boolean htmlFlag;
    private String signName;
    private String templateId;
}

