package com.snail.xpxl.controller;

import com.snail.xpxl.entity.XpxlModel;
import com.snail.conreoller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xpxl-model")
@Api(value = "型号接口", tags="型号接口")
public class XpxlModelController extends BaseController<XpxlModel> {

}
