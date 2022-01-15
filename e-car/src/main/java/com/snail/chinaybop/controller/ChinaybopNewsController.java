package com.snail.chinaybop.controller;

import com.snail.conreoller.BaseController;
import com.snail.chinaybop.entity.ChinaybopNews;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chinaybop-news")
public class ChinaybopNewsController extends BaseController<ChinaybopNews> {

}
