package com.yeem.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.king.entity.KingModel;
import com.yeem.king.mapper.KingModelMapper;
import com.yeem.king.service.IKingModelService;
import org.springframework.stereotype.Service;

@Service
public class KingModelServiceImpl extends ServiceImpl<KingModelMapper, KingModel> implements IKingModelService {

}
