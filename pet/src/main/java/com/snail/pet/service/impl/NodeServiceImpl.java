package com.snail.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.pet.entity.Node;
import com.snail.pet.mapper.NodeMapper;
import com.snail.pet.service.INodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements INodeService {

}
