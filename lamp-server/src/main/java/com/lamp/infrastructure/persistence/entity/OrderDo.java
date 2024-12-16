package com.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lamp.domain.entity.Order;
import com.lamp.domain.objvalue.Plan;
import com.lamp.domain.objvalue.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_order", autoResultMap = true)
public class OrderDo extends BaseDo {
    private String orderNo;
    private String type;
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
    private Integer period;
    private BigDecimal price;
    private String remark;
    private String tradeNo;

    public static OrderDo init(Order order) {
        OrderDo orderDo = new OrderDo();
        BeanUtil.copyProperties(order, orderDo);
        orderDo.setDataTraffic(order.getPlan().getBandwidth());
        orderDo.setPeriod(order.getPlan().getPeriod());
        orderDo.setPrice(order.getPlan().getPrice());
        orderDo.setType(order.getProductType().getType());
        return orderDo;
    }

    public Order convertOrder() {
        Order order = new Order();
        BeanUtil.copyProperties(this, order);
        Plan plan = new Plan();
        plan.setBandwidth(this.dataTraffic);
        plan.setPeriod(this.period);
        plan.setPrice(this.price);
        order.setPlan(plan);
        order.setProductType(ProductType.init(this.type));
        return order;
    }
}
