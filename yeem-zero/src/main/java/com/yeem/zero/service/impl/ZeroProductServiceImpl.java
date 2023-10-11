package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.zero.entity.ZeroProduct;
import com.yeem.zero.mapper.ZeroProductMapper;
import com.yeem.zero.service.IZeroProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Service
public class ZeroProductServiceImpl extends ServiceImpl<ZeroProductMapper, ZeroProduct> implements IZeroProductService {

    @Autowired
    private ZeroProductMapper zeroProductMapper;

    @Override
    public List<ZeroProduct> listByName(String name) {
        QueryWrapper<ZeroProduct> zeroProductQueryWrapper = new QueryWrapper<>();
        zeroProductQueryWrapper.like("name", name);
        zeroProductQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), 0);
        List<ZeroProduct> zeroProductList = zeroProductMapper.selectList(zeroProductQueryWrapper);
        zeroProductList.forEach(zeroProduct -> zeroProduct.dealProductImage(zeroProduct));
        return zeroProductList;
    }

    @Override
    public List<ZeroProduct> listByCategoryId(Long categoryId) {
        List<ZeroProduct> zeroProductList = zeroProductMapper.selectByCategoryId(categoryId);
        zeroProductList.forEach(zeroProduct -> zeroProduct.dealProductImage(zeroProduct));
        return zeroProductList;
    }

    /**
     * 获取推荐商品列表
     *
     * @return 推荐商品列表
     */
    @Override
    public List<ZeroProduct> recommend() {
        QueryWrapper<ZeroProduct> zeroProductQueryWrapper = new QueryWrapper<>();
        zeroProductQueryWrapper.like("recommend_flag", 1);
        zeroProductQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), 0);
        List<ZeroProduct> zeroProductList = zeroProductMapper.selectList(zeroProductQueryWrapper);
        zeroProductList.forEach(zeroProduct -> zeroProduct.dealProductImage(zeroProduct));
        return zeroProductList;
    }

    @Override
    public ZeroProduct getById(Long id) {
        ZeroProduct zeroProduct = zeroProductMapper.selectById(id);
        zeroProduct.dealProductImage(zeroProduct);
        return zeroProduct;
    }

    @Override
    public boolean save(ZeroProduct zeroProduct) {
        zeroProductMapper.insert(zeroProduct);
        return true;
    }

    @Override
    public boolean update(ZeroProduct zeroProduct) {
        zeroProductMapper.updateById(zeroProduct);
        return true;
    }

    /**
     * 软删除
     *
     * @param zeroProduct 产品信息
     */

    @Override
    public void removeProduct(ZeroProduct zeroProduct) {
        UpdateWrapper<ZeroProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", zeroProduct.getId()).set("delete_flag", true);
        zeroProductMapper.update(null, updateWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        UpdateWrapper<ZeroProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("id", id)
                .set("delete_flag", true);
        return SqlHelper.retBool(zeroProductMapper.update(null, updateWrapper));
    }
}
