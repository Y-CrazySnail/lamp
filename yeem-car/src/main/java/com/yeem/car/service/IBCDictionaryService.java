package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.BCDictionary;

import java.util.List;

public interface IBCDictionaryService extends IService<BCDictionary> {
    List<BCDictionary> list(String dictType);
    List<BCDictionary> list(String dictType, String tenantNo);
    List<BCDictionary> list(String dictType, String tenantNo, String productNo);
}
