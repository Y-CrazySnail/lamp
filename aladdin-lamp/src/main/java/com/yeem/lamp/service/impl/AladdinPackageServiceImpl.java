package com.yeem.lamp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.lamp.entity.AladdinPackage;
import com.yeem.lamp.mapper.AladdinPackageMapper;
import com.yeem.lamp.service.IAladdinPackageService;
import org.springframework.stereotype.Service;

@DS("proxy")
@Service
public class AladdinPackageServiceImpl extends ServiceImpl<AladdinPackageMapper, AladdinPackage> implements IAladdinPackageService {

}
