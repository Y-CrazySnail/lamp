package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.GameCampaign;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
public class GameCampaignController extends BaseController<GameCampaign> {

}
