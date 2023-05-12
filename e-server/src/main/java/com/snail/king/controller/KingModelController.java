package com.snail.king.controller;

import com.snail.conreoller.BaseController;
import com.snail.king.entity.KingModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/king-model")
public class KingModelController extends BaseController<KingModel> {

}
