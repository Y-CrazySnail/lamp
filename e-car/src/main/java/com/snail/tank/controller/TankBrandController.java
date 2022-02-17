package com.snail.tank.controller;

import com.snail.tank.entity.TankBrand;
import com.snail.conreoller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tank-brand")
@Api(value = "品牌接口", tags="品牌接口")
public class TankBrandController extends BaseController<TankBrand> {

}
