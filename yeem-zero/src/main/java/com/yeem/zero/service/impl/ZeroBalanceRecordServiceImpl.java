package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.ZeroBalanceRecord;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroBalanceRecordMapper;
import com.yeem.zero.security.WechatAuthInterceptor;
import com.yeem.zero.service.IZeroBalanceRecordService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ZeroBalanceRecordServiceImpl extends ServiceImpl<ZeroBalanceRecordMapper, ZeroBalanceRecord> implements IZeroBalanceRecordService {

    @Autowired
    private ZeroBalanceRecordMapper zeroBalanceRecordMapper;

    @Override
    public List<ZeroBalanceRecord> listByUserId(Long userId) {
        QueryWrapper<ZeroBalanceRecord> zeroBalanceRecordQueryWrapper = new QueryWrapper<>();
        zeroBalanceRecordQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        zeroBalanceRecordQueryWrapper.eq("user_id", userId);
        zeroBalanceRecordQueryWrapper.orderByDesc(BaseEntity.BaseField.CREATE_TIME.getName());
        return zeroBalanceRecordMapper.selectList(zeroBalanceRecordQueryWrapper);
    }
}
