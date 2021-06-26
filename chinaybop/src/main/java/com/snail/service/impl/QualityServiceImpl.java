package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Quality;
import com.snail.mapper.QualityMapper;
import com.snail.service.IQualityService;
import org.springframework.stereotype.Service;

@Service
public class QualityServiceImpl extends ServiceImpl<QualityMapper, Quality> implements IQualityService {

}
