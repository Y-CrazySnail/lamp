package com.yeem.aili.controller;

import com.yeem.aili.entity.AiliBrand;
import com.yeem.aili.service.IAiliBrandService;
import com.yeem.common.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aili-brand")
public class AiliBrandController extends BaseController<AiliBrand> {

    @Autowired
    private IAiliBrandService ailiBrandService;
}
