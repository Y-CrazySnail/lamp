package com.yeem.tank.controller;

import com.yeem.tank.entity.TankModel;
import com.yeem.common.conreoller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tank-model")
public class TankModelController extends BaseController<TankModel> {

}
