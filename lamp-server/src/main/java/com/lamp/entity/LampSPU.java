package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_spu")
public class LampSPU extends BaseEntity {
    private String spuType;
    private String spuName;
    @TableField(exist = false)
    private List<LampSKU> skuList;
}
