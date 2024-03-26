package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneCategory;
import com.yeem.one.entity.OneStore;
import com.yeem.one.mapper.OneCategoryMapper;
import com.yeem.one.service.IOneCategoryService;
import com.yeem.one.service.IOneStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneCategoryServiceImpl extends ServiceImpl<OneCategoryMapper, OneCategory> implements IOneCategoryService {

    @Autowired
    private IOneStoreService oneStoreService;

    @Autowired
    private OneCategoryMapper mapper;

    @Override
    public List<OneCategory> listForWechat(OneCategory category) {
        if (null == category.getStoreId()) {
            List<OneStore> storeList = oneStoreService.listByTenantId(category.getTenantId());
            if (storeList.isEmpty()) {
                log.error("store is null");
                return null;
            } else {
                category.setStoreId(storeList.get(0).getId());
            }
        }
        LambdaQueryWrapper<OneCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneCategory::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.eq(OneCategory::getTenantId, category.getTenantId());
        queryWrapper.eq(OneCategory::getStoreId, category.getStoreId());
        queryWrapper.orderByAsc(OneCategory::getCategorySort);
        return mapper.selectList(queryWrapper);
    }
}
