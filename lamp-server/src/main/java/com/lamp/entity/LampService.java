package com.lamp.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

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

    @TableField(exist = false)
    private List<LampServiceMonth> serviceMonthList;

    public static LampService generate(LampMember member) {
        Date current = new Date();
        LampService service = new LampService();
        service.setMemberId(member.getId());
        service.setUuid(UUID.randomUUID().toString());
        service.setEndDate(current);
        service.setBandwidth(0L);
        service.setPeriod(0);
        return service;
    }
}
