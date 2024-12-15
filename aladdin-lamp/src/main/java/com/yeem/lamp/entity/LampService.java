package com.yeem.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_service")
public class LampService extends BaseEntity {

    private Long memberId; // 会员ID

    private String uuid; // UUID

    private Date endDate; // 结束日期

    private Long bandwidth; // 流量（月）

    private Integer period; // 周期（月）

    private Date lastSyncTime; // 最后同步时间

}
