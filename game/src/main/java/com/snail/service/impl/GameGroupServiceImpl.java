package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.GameGroup;
import com.snail.mapper.GameGroupMapper;
import com.snail.service.IGameGroupService;
import org.springframework.stereotype.Service;

@Service
public class GameGroupServiceImpl extends ServiceImpl<GameGroupMapper, GameGroup> implements IGameGroupService {
}
