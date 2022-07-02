package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.GameUser;
import com.snail.mapper.GameUserMapper;
import com.snail.service.IGameUserService;
import org.springframework.stereotype.Service;

@Service
public class GameUserServiceImpl extends ServiceImpl<GameUserMapper, GameUser> implements IGameUserService {

}
