package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController<Message> {

}
