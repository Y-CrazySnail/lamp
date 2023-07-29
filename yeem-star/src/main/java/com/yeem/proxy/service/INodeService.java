package com.yeem.proxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.proxy.entity.Node;

public interface INodeService extends IService<Node> {
    void generateNode();
}
