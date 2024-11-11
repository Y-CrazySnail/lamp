package com.yeem.car.service.wechat;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.BCDictionary;
import com.yeem.car.entity.BCModel;
import com.yeem.car.mapper.BCModelMapper;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.yeem.car.entity.BCDictionary.DICT_CAR_LEVEL;

@Service
public class WechatBCModelService extends ServiceImpl<BCModelMapper, BCModel> {

    @Autowired
    private WechatBCDictionaryService dictionaryService;

    @Autowired
    private BCModelMapper modelMapper;

    public List<BCModel> list(Long brandId) {
        LambdaQueryWrapper<BCModel> queryWrapper = Wrappers.lambdaQuery(BCModel.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(BCModel::getBrandId, brandId);
        queryWrapper.orderByAsc(BCModel::getNameEn);
        List<BCModel> modelList = modelMapper.selectList(queryWrapper);
        List<BCDictionary> dictionaryList = dictionaryService.list(DICT_CAR_LEVEL);
        Map<String, BCDictionary> dictionaryMap = dictionaryList.stream().collect(Collectors.toMap(BCDictionary::getDictKey, Function.identity()));
        for (BCModel model : modelList) {
            model.setLevelName(dictionaryMap.get(model.getLevelNo()).getDictValue());
        }
        return modelList;
    }
}
