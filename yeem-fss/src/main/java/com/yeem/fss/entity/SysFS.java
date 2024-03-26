package com.yeem.fss.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_fs", autoResultMap = true)
@Data
public class SysFS extends BaseEntity {
    private String fsType;
    private String fsKey;
    private String fsBusiness;
    private String fsUrl;
    private String fsDescription;

    public SysFS(String fsBusiness) {
        this.fsBusiness = fsBusiness;
    }
}
