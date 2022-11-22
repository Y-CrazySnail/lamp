package com.snail.aili.controller;

import com.snail.aili.entity.AiliMessage;
import com.snail.conreoller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aili-message")
public class AiliMessageController extends BaseController<AiliMessage> {

}
