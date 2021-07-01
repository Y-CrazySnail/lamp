package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Model;
import com.snail.entity.Price;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/model")
@Api(value = "型号接口", tags="型号接口")
public class ModelController extends BaseController<Model> {

}
