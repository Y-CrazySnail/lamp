package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Model;
import com.snail.entity.Price;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/model")
public class ModelController extends BaseController<Model> {

}
