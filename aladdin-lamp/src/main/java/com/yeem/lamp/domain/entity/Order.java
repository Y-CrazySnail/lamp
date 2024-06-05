package com.yeem.lamp.domain.entity;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private Long id;
    private String orderNo;
    private Long memberId;
    @TableField(exist = false)
    private Long packageId;
    /**
     * 订单状态 订单状态 0已关闭 -1已生成 1已支付
     */
    private String status;
    private Long serviceId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date completeTime;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;
    private String remark;
    private String tradeNo;

    public void createOrder() {
        this.orderNo = "No" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + this.getMemberId();
        this.orderTime = new Date();
        this.setStatus("-1");


        orderDTO.setOrderNo(orderNo);
        orderDTO.setOrderTime(new Date());
        orderDTO.setStatus("-1");
        orderDTO.setDataTraffic(packageDo.getDataTraffic());
        orderDTO.setPeriod(packageDo.getPeriod());
        orderDTO.setPrice(packageDo.getPrice());
    }
}
