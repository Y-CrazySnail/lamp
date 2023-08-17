package com.yeem.common.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.im.entity.SysTemplate;
import com.yeem.common.im.mapper.SysTemplateMapper;
import com.yeem.common.im.service.ISysTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysTemplateServiceImpl extends ServiceImpl<SysTemplateMapper, SysTemplate> implements ISysTemplateService {

    @Override
    public SysTemplate get(String type, String name) {
        QueryWrapper<SysTemplate> sysTemplateQueryWrapper = new QueryWrapper<>();
        sysTemplateQueryWrapper.eq("name", name);
        if (StringUtils.isEmpty(type)){
            type="mail";
        }
        sysTemplateQueryWrapper.eq("type", type);
        sysTemplateQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), 0);
        return super.getOne(sysTemplateQueryWrapper);
    }
}

