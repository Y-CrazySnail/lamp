package com.yeem.zero.config;

public class Constant {
    /**
     * direct 直接下单
     * indirect (间接下单) 购物车下单
     */
    public static final String ORDER_TYPE_DIRECT = "direct";
    public static final String ORDER_TYPE_INDIRECT = "indirect";

    /**
     * 1已下单|待付款
     * 2已付款|待发货
     * 3已发货|待收货
     * 4已收货|待评价
     * 5已完成
     * 0交易关闭
     * -1退款中
     */
    public static final String ORDER_STATUS_ORDER = "1";
    public static final String ORDER_STATUS_PAY = "2";
    public static final String ORDER_STATUS_DELIVERY = "3";
    public static final String ORDER_STATUS_RECEIVE = "4";
    public static final String ORDER_STATUS_FINISH = "5";
    public static final String ORDER_STATUS_CLOSE = "0";
    public static final String ORDER_STATUS_REFUND = "-1";
}
