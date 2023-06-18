package com.snail.proxy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

@TableName(value = "proxy_share_node", autoResultMap = true)
public class ShareNode extends BaseEntity {
    private String shareUrl;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
