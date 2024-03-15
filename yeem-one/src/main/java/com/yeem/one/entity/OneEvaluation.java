package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_evaluation", autoResultMap = true)
@Data
public class OneEvaluation extends BaseEntity {
    private Long tenantId;
    private Long userId;
    private Long orderId;
    private Long spuId;
    private Long skuId;
    private Integer evaluationRating;
    private String evaluationContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date evaluationTime;
    private String evaluationImage;
}