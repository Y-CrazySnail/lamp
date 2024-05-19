package com.yeem.game.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "game_data_record", autoResultMap = true)
@Data
public class GameDataRecord extends BaseEntity {
    private Long dataId;
    // 成员名，VARCHAR(255) 类型
    private String memberName;

    // 贡献排行，INT 类型
    private int contributionRank;

    // 本周贡献，BIGINT 类型，用于存储大数值
    private long contributionThisWeek;

    // 本周战功，INT 类型
    private int meritThisWeek;

    // 本周助攻，INT 类型
    private int assistsThisWeek;

    // 本周捐献，INT 类型
    private int donationsThisWeek;

    // 贡献总量，BIGINT 类型
    private long totalContribution;

    // 战功总量，INT 类型
    private int totalMerit;

    // 助攻总量，INT 类型
    private int totalAssists;

    // 捐献总量，INT 类型
    private int totalDonations;

    // 势力值，INT 类型
    private int influence;

    // 所属州，VARCHAR(255) 类型
    private String state;

    // 分组，VARCHAR(255) 类型
    private String groupName;
}