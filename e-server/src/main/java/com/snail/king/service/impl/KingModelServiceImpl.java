package com.snail.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.king.entity.KingModel;
import com.snail.king.mapper.KingModelMapper;
import com.snail.king.service.IKingModelService;
import org.springframework.stereotype.Service;

@Service
public class KingModelServiceImpl extends ServiceImpl<KingModelMapper, KingModel> implements IKingModelService {

}
