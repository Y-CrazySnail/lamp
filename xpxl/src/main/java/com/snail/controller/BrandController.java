package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Brand;
import com.snail.entity.Price;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
@Api(value = "品牌接口", tags="品牌接口")
public class BrandController extends BaseController<Brand> {

}
