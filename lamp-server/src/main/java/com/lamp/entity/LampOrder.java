package com.lamp.entity;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_order")
public class LampOrder extends BaseEntity {

    public static final String TYPE_DATA = "data";
    public static final String TYPE_REGULAR = "regular";

    private String orderNo;
    private String type;
    private Long memberId;
    @TableField(exist = false)
    private Long packageId;
    /**
     * 订单状态 订单状态 0已关闭 -1已生成 1已支付
     */
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completeTime;
    private Long bandwidth;
    private Integer period;
    private BigDecimal price;
    private String remark;
    private String tradeNo;


    public void createOrder(Long bandwidth, Integer period, BigDecimal price, String productType) {
        this.orderNo = "No" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + this.getMemberId();
        this.orderTime = new Date();
        this.status = LampOrder.STATUS.TODO.value;
        this.bandwidth = bandwidth;
        this.period = period;
        this.price = price;
        this.type = productType;
    }

    /**
     * 订单状态
     */
    public enum STATUS {
        /**
         * 0已关闭
         */
        CLOSE("0"),
        /**
         * -1已生成
         */
        TODO("-1"),
        /**
         * 1已支付
         */
        ED("1");
        private final String value;

        STATUS(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public void finish() {
        this.completeTime = new Date();
        this.status = LampOrder.STATUS.ED.value;
    }
}
