package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_reward_record")
public class LampRewardRecord extends BaseEntity {
    private Long referrerId;
    private Long refereeId;
    private String refereeEmail;
    private Long orderId;
    private Integer rewardDay;
    private LocalDate rewardDate;

    public static LampRewardRecord init(LampMember referrer, LampMember referee, LampOrder order, int rewardDays) {
        LampRewardRecord rewardRecord = new LampRewardRecord();
        rewardRecord.setReferrerId(referrer.getId());
        rewardRecord.setRefereeId(referee.getId());
        rewardRecord.setRefereeEmail(referee.getEmail());
        rewardRecord.setOrderId(order.getId());
        rewardRecord.setRewardDay(rewardDays);
        rewardRecord.setRewardDate(LocalDate.now());
        return rewardRecord;
    }
}
