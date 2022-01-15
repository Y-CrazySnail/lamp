package com.snail.xpxl.controller;

import com.snail.xpxl.entity.XpxlBrand;
import com.snail.conreoller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xpxl-brand")
@Api(value = "品牌接口", tags="品牌接口")
public class XpxlBrandController extends BaseController<XpxlBrand> {

}
