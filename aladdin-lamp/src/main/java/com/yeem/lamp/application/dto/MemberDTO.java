package com.yeem.lamp.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Service;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MemberDTO {
    private Long id;
    private String email;
    private String wechat;
    private String remark;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateSubscription;
    private List<Service> serviceList;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.wechat = member.getWechat();
        this.remark = member.getRemark();
        this.password = member.getPassword();
        this.lastUpdateSubscription = member.getLastUpdateSubscription();
    }
}
