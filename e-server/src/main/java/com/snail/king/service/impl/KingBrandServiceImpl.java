package com.snail.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.king.entity.KingBrand;
import com.snail.king.mapper.KingBrandMapper;
import com.snail.king.service.IKingBrandService;
import org.springframework.stereotype.Service;

@Service
public class KingBrandServiceImpl extends ServiceImpl<KingBrandMapper, KingBrand> implements IKingBrandService {

}
