package com.yeem.zero.schedule;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 */
@Slf4j
@Component
@EnableScheduling
public class ScheduleTask {

    @Autowired
    private IZeroOrderService zeroOrderService;

    /**
     * 关闭订单定时任务
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void testScheduleTask() {
        log.debug("scan for order waiting to be closed：{}", new Date());
        try {
            UpdateWrapper<ZeroOrder> zeroOrderUpdateWrapper = new UpdateWrapper<>();
            zeroOrderUpdateWrapper.set("status", Constant.ORDER_STATUS_CLOSE);
            zeroOrderUpdateWrapper.set("close_time", new Date());
            zeroOrderUpdateWrapper.le("order_time", DateUtil.offsetHour(new Date(), -1).toJdkDate());
            zeroOrderUpdateWrapper.eq("status", Constant.ORDER_STATUS_ORDER);
            zeroOrderService.update(zeroOrderUpdateWrapper);
        } catch (Exception e) {
            log.debug("scan for order waiting to be closed error", e);
        }
    }
}