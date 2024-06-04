package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.security.Constant;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinOrder;
import com.yeem.lamp.infrastructure.persistence.entity.PackageDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.PackageMapper;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AladdinPackageServiceImpl extends ServiceImpl<PackageMapper, PackageDo> implements IAladdinPackageService {

    @Autowired
    private PackageMapper packageMapper;

    @Override
    public List<PackageDo> list() {
        QueryWrapper<PackageDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return super.list(queryWrapper);
    }

    @Override
    public IPage<PackageDo> pages(int current, int size) {
        QueryWrapper<PackageDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<PackageDo> page = new Page<>(current, size);
        return packageMapper.selectPage(page, queryWrapper);
    }

    /**
     * 下单
     * @param aladdinOrder 订单信息
     * @return 下单结果
     */
    @Override
    public AladdinOrder order(AladdinOrder aladdinOrder) {
        return null;
    }
}
