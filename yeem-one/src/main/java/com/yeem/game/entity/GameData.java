package com.yeem.game.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "game_data", autoResultMap = true)
@Data
public class GameData extends BaseEntity {
    private String allianceName;
    private Date uploadTime;
}