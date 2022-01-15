package com.snail.chinaybop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.chinaybop.entity.Node;

public interface INodeService extends IService<Node> {
    String getConfiguration();
}
