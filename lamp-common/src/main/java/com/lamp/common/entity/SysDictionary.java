package com.lamp.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dictionary")
public class SysDictionary extends BaseEntity {
    private String dictType;
    private String dictKey;
    private String dictValue;
    private String description;
    private String additiveAttribute;
}
