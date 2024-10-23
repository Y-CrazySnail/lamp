package com.yeem.car.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFPriceConfig;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.mapper.CFPriceConfigMapper;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ManageCFPriceConfigService extends ServiceImpl<CFPriceConfigMapper, CFPriceConfig> {

    @Autowired
    private CFPriceConfigMapper priceConfigMapper;

    public void setPriceConfigList(CFProduct product) {
        LambdaQueryWrapper<CFPriceConfig> queryWrapper = Wrappers.lambdaQuery(CFPriceConfig.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(CFPriceConfig::getTenantNo, product.getTenantNo());
        queryWrapper.eq(CFPriceConfig::getProductType, product.getProductType());
        List<CFPriceConfig> priceConfigList = priceConfigMapper.selectList(queryWrapper);
        product.setPriceConfigList(priceConfigList);
    }

    public void savePriceConfigList(CFProduct product) {
        List<CFPriceConfig> priceConfigList = product.getPriceConfigList();
        setPriceConfigList(product);
        List<CFPriceConfig> oldPriceConfigList = product.getPriceConfigList();
        Map<Long, CFPriceConfig> priceConfigMap = oldPriceConfigList.stream().collect(Collectors.toMap(CFPriceConfig::getId, Function.identity()));
        for (CFPriceConfig priceConfig : priceConfigList) {
            if (Objects.isNull(priceConfig.getId())) {
                priceConfig.setTenantNo(product.getTenantNo());
                priceConfig.setProductType(product.getProductType());
                priceConfigMapper.insert(priceConfig);
            } else {
                priceConfigMapper.updateById(priceConfig);
                priceConfigMap.remove(priceConfig.getId());
            }
        }
        for (Long id : priceConfigMap.keySet()) {
            priceConfigMapper.deleteById(id);
        }
    }
}
