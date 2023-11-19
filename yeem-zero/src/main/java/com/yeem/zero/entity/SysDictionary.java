package com.yeem.zero.entity;

import com.yeem.common.entity.BaseEntity;
import lombok.Data;

@Data
public class SysDictionary extends BaseEntity {
    /**
     * 类型
     */
    private String dictType;
    /**
     * 字典key
     */
    private String dictKey;
    /**
     * 字典value
     */
    private String dictValue;
    /**
     * 描述
     */
    private String description;
}
