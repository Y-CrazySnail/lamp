package com.lamp.domain.objvalue;

import lombok.Data;

import java.util.Date;

@Data
public class ServiceTransform {
    private Long id;
    private Long serviceId;
    private Plan originPlan;
    private Date originEndDate;
    private Plan targetPlan;
    private Date targetEndDate;
    private Date transformDate;
}
