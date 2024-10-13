package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.mapper.CFProductMapper;
import com.yeem.car.service.ICFProductService;
import org.springframework.stereotype.Service;

@Service
public class CFProductServiceImpl extends ServiceImpl<CFProductMapper, CFProduct> implements ICFProductService {

}
