package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "base_car_dictionary")
public class BaseCarDictionary extends BaseEntity {

    private String dictKey;
    private String dictValue;
    private String dictLabel;
    private String dictType;
    private String productNo;
    private String tenantNo;
}
