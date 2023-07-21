package com.yeem.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.entity.BaseEntity;

@TableName(value = "sys_permission", autoResultMap = true)
public class Permission extends BaseEntity {

    private String url;
    private String name;
    private String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
