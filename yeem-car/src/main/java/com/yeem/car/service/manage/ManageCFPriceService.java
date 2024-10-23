package com.yeem.car.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.BCDictionary;
import com.yeem.car.entity.CFPrice;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.mapper.CFPriceMapper;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.yeem.car.entity.BCDictionary.DICT_CAR_LEVEL;

@Service
public class ManageCFPriceService extends ServiceImpl<CFPriceMapper, CFPrice> {

    @Autowired
    private ManageBCDictionaryService dictionaryService;

    @Autowired
    private ManageCFTenantService tenantService;

    @Autowired
    private CFPriceMapper priceMapper;

    public void setPriceList(CFProduct product) {
        LambdaQueryWrapper<CFPrice> queryWrapper = Wrappers.lambdaQuery(CFPrice.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        tenantService.auth(queryWrapper);
        queryWrapper.eq(CFPrice::getProductNo, product.getProductNo());
        List<CFPrice> priceList = priceMapper.selectList(queryWrapper);
        Map<String, CFPrice> priceMap = priceList.stream().collect(Collectors.toMap(CFPrice::getLevelNo, Function.identity()));
        List<BCDictionary> carLevelList = dictionaryService.list(DICT_CAR_LEVEL);
        List<CFPrice> templatePriceList = new ArrayList<>();
        for (BCDictionary carLevel : carLevelList) {
            if (priceMap.containsKey(carLevel.getDictKey())) {
                CFPrice price = priceMap.get(carLevel.getDictKey());
                price.setLevelName(carLevel.getDictValue());
                templatePriceList.add(price);
            } else {
                CFPrice price = new CFPrice();
                price.setTenantNo(product.getTenantNo());
                price.setProductNo(product.getProductNo());
                price.setLevelNo(carLevel.getDictKey());
                price.setLevelName(carLevel.getDictValue());
                templatePriceList.add(price);
            }
        }
        product.setPriceList(templatePriceList);
    }

    public void savePriceList(CFProduct product) {
        List<CFPrice> priceList = product.getPriceList();
        for (CFPrice price : priceList) {
            price.setProductNo(product.getProductNo());
            if (Objects.isNull(price.getId())) {
                priceMapper.insert(price);
            } else {
                priceMapper.updateById(price);
            }
        }
    }
}
