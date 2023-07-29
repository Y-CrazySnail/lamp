package com.yeem.chinaybop.controller;

import com.yeem.common.conreoller.BaseController;
import com.yeem.chinaybop.entity.ChinaybopNews;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chinaybop-news")
public class ChinaybopNewsController extends BaseController<ChinaybopNews> {

}
