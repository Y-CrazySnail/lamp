package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneCategory;

import java.util.List;

public interface IOneCategoryService extends IService<OneCategory> {
    List<OneCategory> listForWechat(OneCategory category);
}
