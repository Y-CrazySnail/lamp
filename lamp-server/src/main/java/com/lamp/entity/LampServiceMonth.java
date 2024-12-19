package com.lamp.entity;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import com.lamp.xui.entity.XuiVmessSetting;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_service_month")
public class LampServiceMonth extends BaseEntity {
    private Long serviceId; // 服务ID

    private String uuid;

    private Integer serviceYear; // 年份

    private Integer serviceMonth; // 月份

    private Long bandwidth; // 流量

    private Long bandwidthUp; // 上行流量

    private Long bandwidthDown; // 下行流量

    @TableField(exist = false)
    private List<LampClientTraffic> clientTrafficList;

    /**
     * 计算流量
     */
    public void calculateClientTraffic() {
        bandwidthUp = 0L;
        bandwidthDown = 0L;
        if (clientTrafficList != null) {
            for (LampClientTraffic clientTraffic : clientTrafficList) {
                bandwidthUp += clientTraffic.getXuiUp();
                bandwidthDown += clientTraffic.getXuiDown();
            }
        }
    }

    public static LampServiceMonth generate(LampService service) {
        Date current = new Date();
        LampServiceMonth serviceMonth = new LampServiceMonth();
        serviceMonth.setServiceId(service.getId());
        serviceMonth.setUuid(service.getUuid());
        serviceMonth.setServiceYear(DateUtil.year(current));
        serviceMonth.setServiceMonth(DateUtil.month(current) + 1);
        int lengthOfMonth = DateUtil.lengthOfMonth(DateUtil.month(current) + 1, false);
        int remainingDay = DateUtil.lengthOfMonth(DateUtil.month(current) + 1, false) - lengthOfMonth;
        serviceMonth.setBandwidth(service.getBandwidth() * remainingDay / lengthOfMonth);
        serviceMonth.setBandwidthUp(0L);
        serviceMonth.setBandwidthDown(0L);
        return serviceMonth;
    }
}
