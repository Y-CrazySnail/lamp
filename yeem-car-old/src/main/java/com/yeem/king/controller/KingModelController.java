package com.yeem.king.controller;

import com.yeem.common.conreoller.BaseController;
import com.yeem.king.entity.KingModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/king-model")
public class KingModelController extends BaseController<KingModel> {

}
