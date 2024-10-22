package com.yeem.car.entity;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "car_film_quality")
public class CFQuality extends BaseCF {
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 质保卡号
     */
    private String qualityCardNo;
    /**
     * 质保状态
     */
    @TableField(exist = false)
    private String state;
    /**
     * 车牌号
     */
    private String plateNo;
    /**
     * 车架号
     */
    private String vin;
    /**
     * 产品代码
     */
    private String productNo;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品级别代码
     */
    private String productLevelNo;
    /**
     * 产品级别名称
     */
    private String productLevelName;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 汽车品牌
     */
    private String carBrand;
    /**
     * 汽车型号
     */
    private String carModel;
    /**
     * 汽车颜色
     */
    private String carColor;
    /**
     * 施工时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date workDate;
    /**
     * 质保有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validityDate;
    /**
     * 质保年限
     */
    private Integer guaranteePeriod;
    /**
     * 施工单位
     */
    private String workCompany;
    /**
     * 施工技师
     */
    private String workStaff;
    /**
     * 施工部位
     */
    private String workPart;
    /**
     * 卷心号
     */
    private String rollNumber;
    /**
     * 盒头号
     */
    private String boxNumber;
    /**
     * 审核标识
     */
    private String approveFlag;
    @TableField(exist = false)
    private String productType;

    public enum State {
        NORMAL("1"),
        EXPIRED("0");
        private final String value;

        State(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public void setState() {
        if (null == this.validityDate) {
            this.state = State.EXPIRED.value;
        }
        if (DateUtil.compare(validityDate, new Date()) > 0) {
            this.state = State.NORMAL.value;
        } else {
            this.state = State.EXPIRED.value;
        }
    }
}
