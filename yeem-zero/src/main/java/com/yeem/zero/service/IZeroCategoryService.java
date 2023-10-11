package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroCategory;

import java.util.List;

public interface IZeroCategoryService extends IService<ZeroCategory> {
    List<ZeroCategory> dict();
}
