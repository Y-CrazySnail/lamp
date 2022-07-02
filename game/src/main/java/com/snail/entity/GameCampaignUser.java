package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

@TableName(value = "game_campaign_army_detail", autoResultMap = true)
public class GameCampaignUser extends BaseEntity {

    /**
     * 战役人员明细ID
     */
    private Long campaignDetailId;
    /**
     * 0器械 1主力
     */
    private Integer type;
    /**
     * 等级1
     */
    private Integer level1;
    /**
     * 等级2
     */
    private Integer level2;
    /**
     * 等级3
     */
    private Integer level3;

    public Long getCampaignDetailId() {
        return campaignDetailId;
    }

    public void setCampaignDetailId(Long campaignDetailId) {
        this.campaignDetailId = campaignDetailId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel1() {
        return level1;
    }

    public void setLevel1(Integer level1) {
        this.level1 = level1;
    }

    public Integer getLevel2() {
        return level2;
    }

    public void setLevel2(Integer level2) {
        this.level2 = level2;
    }

    public Integer getLevel3() {
        return level3;
    }

    public void setLevel3(Integer level3) {
        this.level3 = level3;
    }
}
