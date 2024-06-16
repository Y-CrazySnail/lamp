package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private Date serviceDate;
    private Long serviceDown;
    private Long serviceUp;

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