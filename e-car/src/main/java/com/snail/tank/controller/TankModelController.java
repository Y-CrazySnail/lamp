package com.snail.tank.controller;

import com.snail.tank.entity.TankModel;
import com.snail.conreoller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tank-model")
@Api(value = "型号接口", tags="型号接口")
public class TankModelController extends BaseController<TankModel> {

}
