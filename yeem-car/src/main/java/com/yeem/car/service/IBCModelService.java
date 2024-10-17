package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.BCModel;

import java.util.List;

public interface IBCModelService extends IService<BCModel> {
    List<BCModel> listForWechat(Long brandId);
}
