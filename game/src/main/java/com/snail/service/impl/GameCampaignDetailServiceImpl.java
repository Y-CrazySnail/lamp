package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.GameCampaign;
import com.snail.entity.GameCampaignDetail;
import com.snail.mapper.GameCampaignDetailMapper;
import com.snail.mapper.GameCampaignMapper;
import com.snail.service.IGameCampaignDetailService;
import com.snail.service.IGameCampaignService;
import org.springframework.stereotype.Service;

@Service
public class GameCampaignDetailServiceImpl extends ServiceImpl<GameCampaignDetailMapper, GameCampaignDetail> implements IGameCampaignDetailService {

}
