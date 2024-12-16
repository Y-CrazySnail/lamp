package com.lamp.domain.objvalue;

import lombok.Data;

@Data
public class Subscription {
    private Long id;
    private Long serverId;
    private String host;
    private Integer port;
    private String name;
    private Integer sort;
}
