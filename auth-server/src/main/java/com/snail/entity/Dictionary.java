package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "sys_dictionary", autoResultMap = true)
public class Dictionary extends BaseEntity {
    private String dictKey;
    private String dictValue;
    private String description;

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
