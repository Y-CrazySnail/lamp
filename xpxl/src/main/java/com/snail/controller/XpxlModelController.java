package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.XpxlModel;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/model")
@Api(value = "型号接口", tags="型号接口")
public class XpxlModelController extends BaseController<XpxlModel> {

}
