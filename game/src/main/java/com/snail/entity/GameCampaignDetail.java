package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

import java.util.List;

@TableName(value = "game_campaign_detail", autoResultMap = true)
public class GameCampaignDetail extends BaseEntity {

    /**
     * 战役ID
     */
    private Long campaignId;
    /**
     * 战役名称
     */
    private String campaignName;
    /**
     * 分组ID
     */
    private Long groupId;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 主力数量
     */
    private Integer mainArmyNumber;
    /**
     * 主力等级
     */
    private String mainArmyLevel;
    /**
     * 器械数量
     */
    private Integer siegeArmyNumber;
    /**
     * 器械等级
     */
    private String siegeArmyLevel;
    /**
     * 器械攻城值
     */
    private Integer siegeScoreEach;
    /**
     * 营帐位置
     */
    private String tentPosition;
    /**
     * 是否出勤 0未出勤 1出勤
     */
    private Integer workFlag;
    /**
     * 缺席原因
     */
    private String absentReason;
    /**
     * 主力战报
     */
    private Integer mainReport;
    /**
     * 器械战报
     */
    private Integer siegeReport;

    @TableField(exist = false)
    private List<GameCampaignUser> gameCampaignUserList;

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSiegeScoreEach() {
        return siegeScoreEach;
    }

    public void setSiegeScoreEach(Integer siegeScoreEach) {
        this.siegeScoreEach = siegeScoreEach;
    }

    public String getTentPosition() {
        return tentPosition;
    }

    public void setTentPosition(String tentPosition) {
        this.tentPosition = tentPosition;
    }

    public Integer getWorkFlag() {
        return workFlag;
    }

    public void setWorkFlag(Integer workFlag) {
        this.workFlag = workFlag;
    }

    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason;
    }

    public Integer getMainReport() {
        return mainReport;
    }

    public void setMainReport(Integer mainReport) {
        this.mainReport = mainReport;
    }

    public Integer getSiegeReport() {
        return siegeReport;
    }

    public void setSiegeReport(Integer siegeReport) {
        this.siegeReport = siegeReport;
    }

    public List<GameCampaignUser> getGameCampaignArmyDetailList() {
        return gameCampaignUserList;
    }

    public void setGameCampaignArmyDetailList(List<GameCampaignUser> gameCampaignUserList) {
        this.gameCampaignUserList = gameCampaignUserList;
    }

    public Integer getMainArmyNumber() {
        return mainArmyNumber;
    }

    public void setMainArmyNumber(Integer mainArmyNumber) {
        this.mainArmyNumber = mainArmyNumber;
    }

    public String getMainArmyLevel() {
        return mainArmyLevel;
    }

    public void setMainArmyLevel(String mainArmyLevel) {
        this.mainArmyLevel = mainArmyLevel;
    }

    public Integer getSiegeArmyNumber() {
        return siegeArmyNumber;
    }

    public void setSiegeArmyNumber(Integer siegeArmyNumber) {
        this.siegeArmyNumber = siegeArmyNumber;
    }

    public String getSiegeArmyLevel() {
        return siegeArmyLevel;
    }

    public void setSiegeArmyLevel(String siegeArmyLevel) {
        this.siegeArmyLevel = siegeArmyLevel;
    }
}
