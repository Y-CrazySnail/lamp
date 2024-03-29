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
    private IOneSpuService spuService;
    @Autowired
    private IOneSkuService skuService;

    @Override
    public List<OneCart> listByUserId(Long userId) {
        LambdaQueryWrapper<OneCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneCart::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.eq(OneCart::getUserId, userId);
        List<OneCart> cartList = mapper.selectList(queryWrapper);
        for (OneCart cart : cartList) {
            OneSpu spu = spuService.getById(cart.getSkuId());
            cart.setSpu(spu);
            OneSku sku = skuService.getById(cart.getSkuId());
            cart.setSku(sku);
            cart.setValid(spu.getSpuStatus() && sku.getSkuStatus() && null != sku.getSkuStock() && sku.getSkuStock() >= cart.getQuantity());
        }
        return cartList;
    }

    @Override
    public OneCart getByIdWithOther(Long id) {
        OneCart cart = mapper.selectById(id);
        OneSpu spu = spuService.getById(cart.getSkuId());
        cart.setSpu(spu);
        OneSku sku = skuService.getById(cart.getSkuId());
        cart.setSku(sku);
        cart.setValid(spu.getSpuStatus() && sku.getSkuStatus() && null != sku.getSkuStock() && sku.getSkuStock() >= cart.getQuantity());
        return cart;
    }
}
