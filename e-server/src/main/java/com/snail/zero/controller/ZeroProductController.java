package com.snail.zero.controller;

import com.snail.conreoller.BaseController;
import com.snail.zero.entity.ZeroProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/zero-product")
public class ZeroProductController extends BaseController<ZeroProduct> {

}
