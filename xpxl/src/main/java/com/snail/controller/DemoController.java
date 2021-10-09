package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Demo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Api(value = "案例接口", tags="案例接口")
public class DemoController extends BaseController<Demo> {

}
