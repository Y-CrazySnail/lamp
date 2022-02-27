package com.snail.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

import java.time.LocalDateTime;

@TableName(value = "demo_child_bill", autoResultMap = true)
public class ChildBill extends BaseEntity {

    /**
     * 父账单ID
     */
    private Long sourceId;
    /**
     * 员工ID
     */
    private Long employeeId;
    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 交易金额
     */
    private Long amount;
    /**
     * 交易类型 0：充值 1：退款 2：洗发 3：理发 4：染发 5：烫发 6：护理
     */
    private Long dealType;
    /**
     * 交易时间
     */
    private LocalDateTime dealTime;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDealType() {
        return dealType;
    }

    public void setDealType(Long dealType) {
        this.dealType = dealType;
    }

    public LocalDateTime getDealTime() {
        return dealTime;
    }

    public void setDealTime(LocalDateTime dealTime) {
        this.dealTime = dealTime;
    }
}
