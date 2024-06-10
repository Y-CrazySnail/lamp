package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.entity.NodeVmess;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.hutool.core.util.StrUtil;

import java.util.Date;

/**
 * vmess节点
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_node_vmess", autoResultMap = true)
public class NodeVmessDo extends BaseDo {
    private Long serviceId;
    private Long serverId;
    private Integer serviceYear;
    private Integer serviceMonth;
    private Long serviceDown;
    private Long serviceUp;
    private Date serviceDate;
    private String nodeType;
    private String nodePs;
    private String nodeAdd;
    private String nodePort;
    private String nodeId;
    private String aid;
    private String scy;
    private String net;
    private String type;
    private String host;
    private String path;
    private String tls;
    private String sni;
    private int sort;
    private int multiplyingPower;

    public static NodeVmessDo init(NodeVmess nodeVmess) {
        NodeVmessDo nodeVmessDo = new NodeVmessDo();
        BeanUtil.copyProperties(nodeVmess, nodeVmessDo);
        return nodeVmessDo;
    }

    public NodeVmess convertNodeVmess() {
        NodeVmess nodeVmess = new NodeVmess();
        BeanUtil.copyProperties(this, nodeVmess);
        return nodeVmess;
    }
}