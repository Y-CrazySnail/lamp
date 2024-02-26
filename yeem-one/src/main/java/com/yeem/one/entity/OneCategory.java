package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_category", autoResultMap = true)
@Data
public class OneCategory extends BaseEntity {
    private Long tenantId;
    private String storeId;
    private String categoryName;
}
