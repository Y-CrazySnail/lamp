package com.snail.aili.controller;

import com.snail.aili.entity.AiliModel;
import com.snail.conreoller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aili-model")
public class AiliModelController extends BaseController<AiliModel> {

}
