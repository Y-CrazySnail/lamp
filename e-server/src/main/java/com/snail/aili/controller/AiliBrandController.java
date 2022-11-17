package com.snail.aili.controller;

import com.snail.aili.entity.AiliBrand;
import com.snail.aili.service.IAiliBrandService;
import com.snail.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aili-brand")
public class AiliBrandController extends BaseController<AiliBrand> {

    @Autowired
    private IAiliBrandService ailiBrandService;
}
