package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_service")
public class LampService extends BaseEntity {

    private Long memberId; // 会员ID

    private String uuid; // uuid

    private Integer year; // 年份

    private Integer month; // 月份

    private Long bandwidth; // 流量

    private Long bandwidthUp; // 上行流量

    private Long bandwidthDown; // 下行流量

    public static LampService generate(LampMember member) {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        int year = lastMonth.getYear();
        int month = lastMonth.getMonthValue();
        LampService service = new LampService();
        service.setMemberId(member.getId());
        service.setUuid(member.getUuid());
        service.setYear(year);
        service.setMonth(month);
        return service;
    }
}
