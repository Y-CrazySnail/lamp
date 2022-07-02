package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.GameCampaign;
import com.snail.mapper.GameCampaignMapper;
import com.snail.service.IGameCampaignService;
import org.springframework.stereotype.Service;

@Service
public class GameCampaignServiceImpl extends ServiceImpl<GameCampaignMapper, GameCampaign> implements IGameCampaignService {

}
