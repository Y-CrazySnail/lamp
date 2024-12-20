package com.lamp.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_service")
public class LampService extends BaseEntity {

    private Long memberId; // 会员ID

    private String uuid; // UUID

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expiryDate; // 结束日期

    private Long bandwidth; // 流量（月）

    private Integer period; // 周期（月）

    private Date lastSyncTime; // 最后同步时间

    @TableField(exist = false)
    private List<LampServiceMonth> serviceMonthList;

    /**
     * 已过期
     * @return 是 否
     */
    public boolean isNotExpired() {
        return expiryDate.isAfter(LocalDate.now());
    }
}
