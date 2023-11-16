package com.yeem.car.film.controller.web;

import cn.hutool.http.HttpStatus;
import com.yeem.car.film.entity.BaseCarBrand;
import com.yeem.car.film.log.OperateLog;
import com.yeem.car.film.service.ICarBrandService;
import com.yeem.common.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web")
public class WebBaseCarBrandController extends BaseController<BaseCarBrand> {
    @Autowired
    private ICarBrandService carBrandService;


}
