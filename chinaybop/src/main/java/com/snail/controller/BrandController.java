package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Brand;
import com.snail.entity.Price;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
public class BrandController extends BaseController<Brand> {

}
