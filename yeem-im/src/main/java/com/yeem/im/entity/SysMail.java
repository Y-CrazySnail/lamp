package com.yeem.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_mail")
public class SysMail extends BaseEntity {
    private String fromEmail; // 发件人邮箱
    private String toEmail; // 收件人邮箱
    private String subject; // 邮件主题
    private String content; // 邮件内容
    private String attachment; // 附件
    private String businessName; // 业务名称
    private int businessId; // 业务ID
    private int timingFlag; // 定时发送标志
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timingTime; // 定时发送时间
    private int state; // 状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime; // 发送时间
    private int tryTime; // 尝试次数
    private String exception; // 异常信息
    private boolean htmlFlag;//是不是html
}
