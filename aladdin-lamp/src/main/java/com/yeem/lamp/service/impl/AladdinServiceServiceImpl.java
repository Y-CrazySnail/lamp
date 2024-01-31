package com.yeem.lamp.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinNodeVmess;
import com.yeem.lamp.entity.AladdinService;
import com.yeem.lamp.mapper.AladdinServiceMapper;
import com.yeem.lamp.service.IAladdinNodeVmessService;
import com.yeem.lamp.service.IAladdinServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Service
public class AladdinServiceServiceImpl extends ServiceImpl<AladdinServiceMapper, AladdinService> implements IAladdinServiceService {

    @Autowired
    private AladdinServiceMapper aladdinServiceMapper;
    @Autowired
    private IAladdinNodeVmessService aladdinNodeVmessService;

    @Override
    public List<AladdinService> list() {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return super.list(queryWrapper);
    }

    @Override
    public List<AladdinService> listValid() {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.ge("end_date", new Date());
        List<AladdinService> aladdinServiceList = super.list(queryWrapper);
        for (AladdinService aladdinService : aladdinServiceList) {
            dealDataTraffic(aladdinService);
        }
        aladdinServiceList.removeIf(aladdinService -> aladdinService.getSurplus().contains("-"));
        return aladdinServiceList;
    }

    @Override
    public List<AladdinService> listByMemberId(Long memberId) {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("member_id", memberId);
        List<AladdinService> aladdinServiceList = super.list(queryWrapper);
        for (AladdinService aladdinService : aladdinServiceList) {
            dealDataTraffic(aladdinService);
        }
        return aladdinServiceList;
    }

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<AladdinService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
    }

    @Override
    public IPage<AladdinService> pages(int current, int size,
                                       Long memberId,
                                       String status,
                                       String wechat,
                                       String email) {
        IPage<AladdinService> page = new Page<>(current, size);
        for (AladdinService aladdinService : page.getRecords()) {
            dealDataTraffic(aladdinService);
        }
        return aladdinServiceMapper.selectPages(page, memberId, status, wechat, email);
    }

    @Override
    public AladdinService getByUUID(String uuid) {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("uuid", uuid);
        AladdinService aladdinService = super.getOne(queryWrapper);
        dealDataTraffic(aladdinService);
        return aladdinService;
    }

    @Override
    public void refreshStatus() {
        aladdinServiceMapper.refreshStatus();
    }

    private void dealDataTraffic(AladdinService aladdinService) {
        int year = DateUtil.year(new Date());
        int month = DateUtil.month(new Date()) + 1;
        List<AladdinNodeVmess> aladdinNodeVmessList = aladdinNodeVmessService.listByServiceId(aladdinService.getId(), year, month);
        long up = 0L;
        long down = 0L;
        for (AladdinNodeVmess aladdinNodeVmess : aladdinNodeVmessList) {
            up += aladdinNodeVmess.getServiceUp() * aladdinNodeVmess.getMultiplyingPower();
            down += aladdinNodeVmess.getServiceDown() * aladdinNodeVmess.getMultiplyingPower();
        }
        Double total = ((double) (up + down) / 1024 / 1024 / 1024);
        String surplus = String.format("%.2f", aladdinService.getDataTraffic() - total);
        aladdinService.setSurplus(surplus);
    }
}
