package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.ZeroBalanceRecord;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroBalanceRecordMapper;
import com.yeem.zero.service.IZeroBalanceRecordService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class ZeroOrderBalanceRecordServiceImpl extends ServiceImpl<ZeroBalanceRecordMapper, ZeroBalanceRecord> implements IZeroBalanceRecordService {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Autowired
    private ZeroBalanceRecordMapper zeroBalanceRecordMapper;

    @Override
    public List<ZeroBalanceRecord> listByUsername() {
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save cart");
        }
        QueryWrapper<ZeroBalanceRecord> zeroBalanceRecordQueryWrapper = new QueryWrapper<>();
        zeroBalanceRecordQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        zeroBalanceRecordQueryWrapper.eq("user_id", zeroUserExtra.getUserId());
        zeroBalanceRecordQueryWrapper.orderByDesc(BaseEntity.BaseField.CREATE_TIME.getName());
        return zeroBalanceRecordMapper.selectList(zeroBalanceRecordQueryWrapper);
    }
}
