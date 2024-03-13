package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneCart;
import com.yeem.one.entity.OneSku;
import com.yeem.one.entity.OneSpu;
import com.yeem.one.mapper.OneCartMapper;
import com.yeem.one.service.IOneCartService;
import com.yeem.one.service.IOneSkuService;
import com.yeem.one.service.IOneSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneCartServiceImpl extends ServiceImpl<OneCartMapper, OneCart> implements IOneCartService {

    @Autowired
    private OneCartMapper mapper;
    @Autowired
    private IOneSpuService oneSpuService;
    @Autowired
    private IOneSkuService oneSkuService;

    @Override
    public List<OneCart> listByUserId(Long userId) {
        LambdaQueryWrapper<OneCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneCart::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.eq(OneCart::getUserId, userId);
        List<OneCart> cartList = mapper.selectList(queryWrapper);
        for (OneCart cart : cartList) {
            OneSpu spu = oneSpuService.getById(cart.getSkuId());
            cart.setSpu(spu);
            OneSku sku = oneSkuService.getById(cart.getSkuId());
            cart.setSku(sku);
        }
        return cartList;
    }
}
