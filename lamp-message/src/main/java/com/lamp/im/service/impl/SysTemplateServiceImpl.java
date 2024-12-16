package com.lamp.im.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.im.entity.SysTemplate;
import com.lamp.im.mapper.SysTemplateMapper;
import com.lamp.im.service.ISysTemplateService;
import org.springframework.stereotype.Service;

@Service
public class SysTemplateServiceImpl extends ServiceImpl<SysTemplateMapper, SysTemplate> implements ISysTemplateService {

    @Override
    public SysTemplate get(String type, String name) {
        QueryWrapper<SysTemplate> sysTemplateQueryWrapper = new QueryWrapper<>();
        sysTemplateQueryWrapper.eq("name", name);
        if (StrUtil.isEmpty(type)){
            type="mail";
        }
        sysTemplateQueryWrapper.eq("type", type);
        sysTemplateQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), 0);
        return super.getOne(sysTemplateQueryWrapper);
    }
}

