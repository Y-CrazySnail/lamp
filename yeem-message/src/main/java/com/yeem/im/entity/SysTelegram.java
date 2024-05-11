package com.yeem.im.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_telegram")
public class SysTelegram extends BaseEntity {
    /**
     * TG Chat ID
     */
    private String chatId;

    /**
     * 内容
     */
    private String content;
}
