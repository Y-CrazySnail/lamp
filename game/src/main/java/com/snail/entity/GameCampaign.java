package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.snail.chinaybop.entity.BaseEntity;

import java.time.LocalDateTime;

@TableName(value = "game_campaign", autoResultMap = true)
public class GameCampaign extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 战役时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime campaignTime;

    /**
     * 坐标
     */
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCampaignTime() {
        return campaignTime;
    }

    public void setCampaignTime(LocalDateTime campaignTime) {
        this.campaignTime = campaignTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
