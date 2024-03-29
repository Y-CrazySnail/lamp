package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_order_item", autoResultMap = true)
@Data
public class OneOrderItem extends BaseEntity {
    private Long orderId;
    private Long spuId;
    private String spuName;
    private String spuAttribute;
    private String spuShowImage;
    private Long skuId;
    private String skuName;
    private Integer skuPrice;
    private String skuAttribute;
    private String skuShowImage;
    private Integer orderItemQuantity;
    private Integer orderItemAmount;

    public static OneOrderItem convert(OneCart cart) {
        OneOrderItem orderItem = new OneOrderItem();
        orderItem.setSpuId(cart.getSpu().getId());
        orderItem.setSpuName(cart.getSpu().getSpuName());
        orderItem.setSpuAttribute(cart.getSpu().getSpuAttribute());
        orderItem.setSpuShowImage(cart.getSpu().getSpuShowImage());
        orderItem.setSkuId(cart.getSku().getId());
        orderItem.setSkuName(cart.getSku().getSkuName());
        orderItem.setSkuPrice(cart.getSku().getSkuPrice());
        orderItem.setSkuAttribute(cart.getSku().getSkuAttribute());
        orderItem.setSkuShowImage(cart.getSku().getSkuShowImage());
        orderItem.setOrderItemQuantity(cart.getQuantity());
        orderItem.setOrderItemAmount(cart.getQuantity() * cart.getSku().getSkuPrice());
        return orderItem;
    }
}
