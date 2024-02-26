package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneCategory;
import com.yeem.one.mapper.OneCategoryMapper;
import com.yeem.one.service.IOneCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OneCategoryServiceImpl extends ServiceImpl<OneCategoryMapper, OneCategory> implements IOneCategoryService {

}
