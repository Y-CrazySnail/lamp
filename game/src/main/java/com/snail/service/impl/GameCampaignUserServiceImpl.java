package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.GameCampaignUser;
import com.snail.mapper.GameCampaignUserMapper;
import com.snail.service.IGameCampaignUserService;
import org.springframework.stereotype.Service;

@Service
public class GameCampaignUserServiceImpl extends ServiceImpl<GameCampaignUserMapper, GameCampaignUser> implements IGameCampaignUserService {

}
