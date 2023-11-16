package com.yeem.car.film.controller.wechat;

import com.yeem.car.film.entity.BaseCarBrand;
import com.yeem.car.film.service.ICarBrandService;
import com.yeem.common.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatBaseCarBrandController extends BaseController<BaseCarBrand> {
    @Autowired
    private ICarBrandService carBrandService;


}
