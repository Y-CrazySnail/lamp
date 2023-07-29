package com.yeem.aili.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "aili_quality", autoResultMap = true)
public class AiliQuality extends BaseEntity {
    /**
     * 客户姓名
     */
    private String name;
    /**
     * 客户手机号
     */
    private String phone;
    /**
     * 汽车型号
     */
    private String carModel;
    /**
     * 车牌号
     */
    private String carNumber;
    /**
     * 车架号
     */
    private String carShelfNumber;
    /**
     * 汽车颜色
     */
    private String carColor;
    /**
     * 施工时间
     */
    private String workDateTime;
    /**
     * 施工单位
     */
    private String workCompany;
    /**
     * 施工部位
     */
    private String workPart;
    /**
     * 施工技师
     */
    private String workStaff;
    /**
     * 产品级别
     */
    private String productLevel;
    /**
     * 审核状态 0未审核 1已审核
     */
    private Integer checkStatus;
}
