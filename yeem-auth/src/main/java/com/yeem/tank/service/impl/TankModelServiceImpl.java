package com.yeem.tank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.tank.entity.TankModel;
import com.yeem.tank.mapper.TankModelMapper;
import com.yeem.tank.service.ITankModelService;
import org.springframework.stereotype.Service;

@Service
public class TankModelServiceImpl extends ServiceImpl<TankModelMapper, TankModel> implements ITankModelService {

}
