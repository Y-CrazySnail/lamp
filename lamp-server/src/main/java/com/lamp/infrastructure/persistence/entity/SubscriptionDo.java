package com.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.domain.objvalue.Subscription;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_subscription", autoResultMap = true)
public class SubscriptionDo extends BaseDo {
    private Long serverId;
    private String host;
    private Integer port;
    private String name;
    private Integer sort;

    public static SubscriptionDo init(Subscription subscription) {
        SubscriptionDo subscriptionDo = new SubscriptionDo();
        BeanUtil.copyProperties(subscription, subscriptionDo);
        return subscriptionDo;
    }

    public Subscription convertSubscription() {
        Subscription subscription = new Subscription();
        BeanUtil.copyProperties(this, subscription);
        return subscription;
    }
}
