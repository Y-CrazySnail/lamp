package com.yeem.tank.controller;

import com.yeem.tank.entity.TankBrand;
import com.yeem.common.conreoller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tank-brand")
public class TankBrandController extends BaseController<TankBrand> {

}
