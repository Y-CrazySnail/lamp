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
import java.util.stream.Collectors;

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
        queryWrapper.orderByDesc(OneCart::getId);
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
    public List<OneCart> listByUserIdAndStoreId(Long userId, Long storeId) {
        LambdaQueryWrapper<OneCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneCart::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.eq(OneCart::getUserId, userId);
        queryWrapper.eq(OneCart::getStoreId, storeId);
        queryWrapper.orderByDesc(OneCart::getId);
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

    @Override
    public List<OneCart> saveForWechat(OneCart cart) {
        // 购物车ID不为空|执行更新操作
        if (null != cart.getId()) {
            // 数量为零|删除购物车信息
            if (null == cart.getQuantity() || 0 == cart.getQuantity()) {
                mapper.deleteById(cart);
            } else {
                mapper.updateById(cart);
            }
        } else {
            LambdaQueryWrapper<OneCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OneCart::getTenantId, cart.getTenantId());
            queryWrapper.eq(OneCart::getUserId, cart.getUserId());
            queryWrapper.eq(OneCart::getStoreId, cart.getStoreId());
            queryWrapper.eq(OneCart::getSpuId, cart.getSpuId());
            queryWrapper.eq(OneCart::getSkuId, cart.getSkuId());
            queryWrapper.eq(OneCart::getDeleteFlag, false);
            OneCart checkCart = mapper.selectOne(queryWrapper);
            if (null == checkCart) {
                mapper.insert(cart);
            } else {
                checkCart.setQuantity(cart.getQuantity() + checkCart.getQuantity());
                mapper.updateById(checkCart);
            }
        }
        return listByUserIdAndStoreId(cart.getUserId(), cart.getStoreId());
    }

    @Override
    public List<OneCart> clear(OneCart cart) {
        LambdaQueryWrapper<OneCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneCart::getStoreId, cart.getStoreId());
        queryWrapper.eq(OneCart::getUserId, cart.getUserId());
        List<OneCart> cartList = mapper.selectList(queryWrapper);
        if (!cartList.isEmpty()) {
            mapper.deleteBatchIds(cartList.stream().map(OneCart::getId).collect(Collectors.toList()));
        }
        return listByUserIdAndStoreId(cart.getUserId(), cart.getStoreId());
    }
}
