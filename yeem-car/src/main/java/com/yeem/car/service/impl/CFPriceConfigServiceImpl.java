package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFPriceConfig;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.mapper.CFPriceConfigMapper;
import com.yeem.car.service.ICFPriceConfigService;
import org.springframework.stereotype.Service;

@Service
public class CFPriceConfigServiceImpl extends ServiceImpl<CFPriceConfigMapper, CFPriceConfig> implements ICFPriceConfigService {

    @Override
    public void setPriceConfigList(CFProduct product) {

    }
}
