package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.BCBrand;

import java.util.List;

public interface IBCBrandService extends IService<BCBrand> {
    List<BCBrand> listForWechat();
}
