package com.snail.tank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.tank.entity.TankModel;
import com.snail.tank.mapper.TankModelMapper;
import com.snail.tank.service.ITankModelService;
import org.springframework.stereotype.Service;

@Service
public class TankModelServiceImpl extends ServiceImpl<TankModelMapper, TankModel> implements ITankModelService {

}
