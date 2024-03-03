package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_category", autoResultMap = true)
@Data
public class OneCategory extends BaseEntity {
    private Long tenantId;
    private String storeId;
    @TableField(exist = false)
    private String storeName;
    private String categoryName;
    private Integer categorySort;
}
