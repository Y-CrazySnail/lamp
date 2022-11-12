package com.snail.tank.controller;

import com.snail.tank.entity.TankBrand;
import com.snail.conreoller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tank-brand")
public class TankBrandController extends BaseController<TankBrand> {

}
