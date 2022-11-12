package com.snail.proxy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

@TableName(value = "proxy_traffic", autoResultMap = true)
public class Traffic extends BaseEntity {
    private Long memberId;
    private String type;
    private Long traffic;
    private Integer flag;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTraffic() {
        return traffic;
    }

    public void setTraffic(Long traffic) {
        this.traffic = traffic;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
