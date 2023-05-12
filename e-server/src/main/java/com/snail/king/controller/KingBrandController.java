package com.snail.king.controller;

import com.snail.conreoller.BaseController;
import com.snail.king.entity.KingBrand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/king-brand")
public class KingBrandController extends BaseController<KingBrand> {

}
