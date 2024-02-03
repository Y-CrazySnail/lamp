package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.BaseCarDictionary;

import java.util.List;

public interface ICarDictionaryService extends IService<BaseCarDictionary> {
    List<BaseCarDictionary> list(String productNo);
}
