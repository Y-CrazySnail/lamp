package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

@TableName(value = "game_user", autoResultMap = true)
public class GameUser extends BaseEntity {

    /**
     * 名称
     */
    private String name;
    /**
     * 分组ID
     */
    private Long groupId;
    /**
     * 分组名称
     */
    private String groupName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
