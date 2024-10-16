package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_car_dictionary")
public class BCDictionary extends BaseEntity {

    /**
     * 汽车级别字典
     */
    public static final String DICT_CAR_LEVEL = "car_level";

    private String dictKey;
    private String dictValue;
    private String dictLabel;
    private String dictType;
    private String productNo;
    private String tenantNo;
}
