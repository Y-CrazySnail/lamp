package com.snail.zero.controller;

import com.snail.conreoller.BaseController;
import com.snail.zero.entity.ZeroAddress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zero-address")
public class ZeroAddressController extends BaseController<ZeroAddress> {

}
