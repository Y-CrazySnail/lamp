package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.security.Constant;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinServer;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.AladdinServerMapper;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinNodeVmessService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@DS("proxy")
@Service
public class AladdinServerServiceImpl extends ServiceImpl<AladdinServerMapper, AladdinServer> implements IAladdinServerService {

    @Autowired
    private AladdinServerMapper aladdinServerMapper;
    @Autowired
    private IAladdinNodeVmessService aladdinNodeVmessService;

    @Override
    public List<AladdinServer> list() {
        QueryWrapper<AladdinServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return super.list(queryWrapper);
    }

    @Override
    public IPage<AladdinServer> pages(int current, int size) {
        QueryWrapper<AladdinServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<AladdinServer> page = new Page<>(current, size);
        return aladdinServerMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        aladdinNodeVmessService.updateByServerId((Long) id, Constant.NODE_TYPE_EXPIRED, null, null);
        UpdateWrapper<AladdinServer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq(BaseEntity.BaseField.ID.getName(), id);
        aladdinServerMapper.update(null, updateWrapper);
        return super.removeById(id);
    }

    @Override
    public boolean save(AladdinServer entity) {
        aladdinNodeVmessService.updateByServerId(entity.getId(), null, entity.getSubscribeNamePrefix(), entity.getSort());
        return super.save(entity);
    }
}
