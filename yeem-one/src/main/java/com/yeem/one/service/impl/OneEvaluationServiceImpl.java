package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneEvaluation;
import com.yeem.one.mapper.OneEvaluationMapper;
import com.yeem.one.service.IOneEvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneEvaluationServiceImpl extends ServiceImpl<OneEvaluationMapper, OneEvaluation> implements IOneEvaluationService {

    @Autowired
    private OneEvaluationMapper mapper;

    @Override
    public List<OneEvaluation> listBySpuId(Long spuId) {
        LambdaQueryWrapper<OneEvaluation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneEvaluation::getDeleteFlag, false);
        queryWrapper.eq(OneEvaluation::getSpuId, spuId);
        queryWrapper.orderByDesc(OneEvaluation::getEvaluationTime);
        return mapper.selectList(queryWrapper);
    }
}
