package com.yeem.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_inbound")
public class LampInbound extends BaseEntity {

    private Long serverId; // 服务器ID

    private Double multiplyingPower; // 倍率

    private Long inboundId; // xui入站ID

    private Integer inboundPort; // xui入站端口

    private String inboundProtocol; // xui入站协议

    private String remark; // xui入站备注

    private String settings; // xui入站设置

    private String streamSettings; // xui入站流设置

    private String sniffing; // xui入站监听
}
