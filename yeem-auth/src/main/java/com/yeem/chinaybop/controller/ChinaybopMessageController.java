package com.yeem.chinaybop.controller;

import com.yeem.conreoller.BaseController;
import com.yeem.chinaybop.entity.ChinaybopMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chinaybop-message")
public class ChinaybopMessageController extends BaseController<ChinaybopMessage> {

}
