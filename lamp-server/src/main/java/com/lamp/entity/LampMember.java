package com.lamp.entity;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_member")
public class LampMember extends BaseEntity {

    private String wechat; // 微信

    private String email; // 邮箱

    @TableField(exist = false)
    private String username; // 用户名

    private String password; // 密码

    private String uuid; // uuid

    private Long bandwidth; // 流量（字节）

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastSyncTime; // 最后同步时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expiryDate; //过期时间

    private Integer level; // 会员等级

    private String referrerCode; // 推荐人

    private String referralCode; // 推荐码(本人)

    private String remark; // 备注

    private BigDecimal balance; // 余额

    private Long monthBandwidth; // 本月剩余流量

    private Long monthBandwidthUp; // 本月已使用上行流量

    private Long monthBandwidthDown; // 本月已使用下行流量

    @TableField(exist = false)
    private String token; // token

    @TableField(exist = false)
    private String keywords;

    @TableField(exist = false)
    private List<LampService> serviceList;

    public boolean isValid() {
        return this.expiryDate.isAfter(LocalDate.now()) && monthBandwidth > monthBandwidthDown + monthBandwidthUp;
    }

    public void calculateMonthBandwidth() {
        Date current = new Date();
        int dayOfMonth = DateUtil.dayOfMonth(current);
        int lengthOfMonth = DateUtil.lengthOfMonth(DateUtil.month(current) + 1, false);
        this.monthBandwidth = (long) (((double) (lengthOfMonth - dayOfMonth)) / lengthOfMonth * this.bandwidth);
    }

    public void resetBandwidth() {
        calculateMonthBandwidth();
        monthBandwidthUp = 0L;
        monthBandwidthDown = 0L;
    }
}
