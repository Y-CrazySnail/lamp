package com.yeem.common.im.entity;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_sms")
public class SysSms extends BaseEntity {
    /**
     * 收信息人电话
     */
    private String toPhone;

    /**
     * 短信签名内容
     */
    private String signName;

    /**
     * session内容(无需要可忽略)
     */
    private String sessionContext;

    /**
     * 短信码号扩展(可忽略)
     */
    private String extendCode;

    /**
     * 国内短信无需填写
     */
    private String senderId;

    /**
     * 短信正文
     */
    private String content;

    /**
     * 定时发送标识 0否 1是
     */
    private Integer timingFlag;

    /**
     * 定时发送时间
     */
    private Date timingTime;

    /**
     * 状态 0未发送 1发送成功 9发送失败
     */
    private Integer state;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 业务id
     */
    private Integer businessId;

    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 模板id
     */
    private String templateId;
}
