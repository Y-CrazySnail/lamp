package com.snail.tank.controller;

import com.snail.tank.entity.TankModel;
import com.snail.conreoller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tank-model")
public class TankModelController extends BaseController<TankModel> {

}
