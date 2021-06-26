package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Model;
import com.snail.mapper.ModelMapper;
import com.snail.service.IModelService;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements IModelService {

}
