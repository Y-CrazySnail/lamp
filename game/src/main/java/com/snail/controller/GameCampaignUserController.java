package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.GameCampaignUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign_user")
public class GameCampaignUserController extends BaseController<GameCampaignUser> {

}
