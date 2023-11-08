package com.yeem.lamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.Node;

public interface INodeService extends IService<Node> {
    void generateNode();
}
