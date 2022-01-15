package com.snail.chinaybop.controller;

import com.snail.conreoller.BaseController;
import com.snail.chinaybop.entity.Dictionary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController<Dictionary> {

}
