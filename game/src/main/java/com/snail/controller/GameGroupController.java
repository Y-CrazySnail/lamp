package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.GameGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GameGroupController extends BaseController<GameGroup> {

}
