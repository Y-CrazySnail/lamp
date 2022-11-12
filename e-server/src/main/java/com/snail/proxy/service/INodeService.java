package com.snail.proxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.proxy.entity.Node;

public interface INodeService extends IService<Node> {
    void generateNode();
}
