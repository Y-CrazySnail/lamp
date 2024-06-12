package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDTO {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completeTime;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;
    private String remark;
    private String tradeNo;

    public OrderDTO() {

    }

    public OrderDTO(Order order) {
        BeanUtil.copyProperties(order, this);
    }

    public Order convertOrder() {
        Order order = new Order();
        BeanUtil.copyProperties(this, order);
        return order;
    }

}
