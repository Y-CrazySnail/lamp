package com.yeem.king.controller;

import com.yeem.conreoller.BaseController;
import com.yeem.king.entity.KingBrand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/king-brand")
public class KingBrandController extends BaseController<KingBrand> {

}
