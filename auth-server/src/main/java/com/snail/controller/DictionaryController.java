package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Dictionary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController<Dictionary> {

}
