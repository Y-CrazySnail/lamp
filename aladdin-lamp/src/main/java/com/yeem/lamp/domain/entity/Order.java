package com.yeem.lamp.domain.entity;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.yeem.lamp.domain.objvalue.Plan;
import com.yeem.lamp.domain.objvalue.ProductType;
import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private Long id;
    private Long memberId;
    private Long serviceId;
    private ProductType productType;
    private String orderNo;
    private String tradeNo;
    /**
     * 订单状态 订单状态 0已关闭 -1已生成 1已支付
     */
    private String status;
    private Plan plan;
    private Date orderTime;
    private Date completeTime;
    private String remark;

    public void createOrder(Plan plan, ProductType productType, Long serviceId) {
        this.serviceId = serviceId;
        this.orderNo = "No" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + this.getMemberId();
        this.orderTime = new Date();
        this.setStatus("-1");
        this.productType = productType;
        this.plan = plan;
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
        this.status = STATUS.ED.value;
    }
}
