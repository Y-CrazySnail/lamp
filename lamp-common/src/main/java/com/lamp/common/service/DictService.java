package com.lamp.common.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.common.entity.SysDictionary;
import com.lamp.common.mapper.DictMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DictService extends ServiceImpl<DictMapper, SysDictionary> {

    public List<SysDictionary> getByType(String dictType) {
        LambdaQueryWrapper<SysDictionary> queryWrapper = new LambdaQueryWrapper<>(SysDictionary.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(SysDictionary::getDictType, dictType);
        return baseMapper.selectList(queryWrapper);
    }

    public SysDictionary getByTypeAndKey(String dictType, String dictKey) {
        LambdaQueryWrapper<SysDictionary> queryWrapper = new LambdaQueryWrapper<>(SysDictionary.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(SysDictionary::getDictType, dictType);
        queryWrapper.eq(SysDictionary::getDictKey, dictKey);
        return baseMapper.selectOne(queryWrapper);
    }

    public String getValueByTypeAndKey(String dictType, String dictKey, String defaultValue) {
        SysDictionary dictionary = getByTypeAndKey(dictType, dictKey);
        if (Objects.isNull(dictionary)) {
            return defaultValue;
        } else {
            return dictionary.getDictValue();
        }
    }
}
