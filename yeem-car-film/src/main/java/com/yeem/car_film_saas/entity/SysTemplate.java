package com.yeem.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_template")
public class SysTemplate extends BaseEntity {
    private String name;
    private String type;
    private String subject;
    private String content;
}

