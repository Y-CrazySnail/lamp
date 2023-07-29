package com.yeem.aili.controller;

import com.yeem.aili.entity.AiliMessage;
import com.yeem.conreoller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aili-message")
public class AiliMessageController extends BaseController<AiliMessage> {

}
