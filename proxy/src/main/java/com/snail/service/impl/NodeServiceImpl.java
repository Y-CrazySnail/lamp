package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Node;
import com.snail.mapper.NodeMapper;
import com.snail.service.INodeService;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements INodeService {

}
