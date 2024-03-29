package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneEvaluation;

import java.util.List;

public interface IOneEvaluationService extends IService<OneEvaluation> {
    List<OneEvaluation> listBySpuId(Long spuId);
}
