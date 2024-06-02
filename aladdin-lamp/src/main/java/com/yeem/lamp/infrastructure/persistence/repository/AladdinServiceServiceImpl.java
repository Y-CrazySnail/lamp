package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.security.Constant;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinNodeVmess;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.ServiceMapper;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinNodeVmessService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Service
public class AladdinServiceServiceImpl extends ServiceImpl<ServiceMapper, ServiceDo> implements IAladdinServiceService {

    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private IAladdinNodeVmessService aladdinNodeVmessService;

    @Override
    public List<ServiceDo> listValid() {
        QueryWrapper<ServiceDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.ge("end_date", new Date());
        queryWrapper.eq("type", ServiceDo.TYPE.SERVICE.getValue());
        List<ServiceDo> serviceDoList = super.list(queryWrapper);
        for (ServiceDo serviceDo : serviceDoList) {
            dealDataTraffic(serviceDo);
        }
        serviceDoList.removeIf(aladdinService -> aladdinService.getSurplus().contains("-"));
        return serviceDoList;
    }

    @Override
    public List<ServiceDo> listByMemberId(Long memberId) {
        QueryWrapper<ServiceDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("type", ServiceDo.TYPE.SERVICE.getValue());
        List<ServiceDo> serviceDoList = super.list(queryWrapper);
        for (ServiceDo serviceDo : serviceDoList) {
            dealDataTraffic(serviceDo);
        }
        return serviceDoList;
    }

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<ServiceDo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
    }

    @Override
    public IPage<ServiceDo> pages(int current, int size,
                                  Long memberId,
                                  String status,
                                  String wechat,
                                  String email) {
        IPage<ServiceDo> page = new Page<>(current, size);
        page = serviceMapper.selectPages(page, memberId, status, wechat, email);
        for (ServiceDo serviceDo : page.getRecords()) {
            dealDataTraffic(serviceDo);
        }
        return page;
    }

    @Override
    public ServiceDo getByUUID(String uuid) {
        QueryWrapper<ServiceDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("uuid", uuid);
        queryWrapper.eq("type", ServiceDo.TYPE.SERVICE.getValue());
        ServiceDo serviceDo = super.getOne(queryWrapper);
        dealDataTraffic(serviceDo);
        return serviceDo;
    }

    @Override
    public void refreshStatus() {
        serviceMapper.refreshStatus();
    }

    private void dealDataTraffic(ServiceDo serviceDo) {
        if (ServiceDo.TYPE.DATA.getValue().equals(serviceDo.getType())) {
            return;
        }
        // 查询数据加量包
        QueryWrapper<ServiceDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("member_id", serviceDo.getMemberId());
        queryWrapper.ge("end_date", new Date());
        queryWrapper.eq("type", ServiceDo.TYPE.DATA.getValue());
        List<ServiceDo> serviceDoList = super.list(queryWrapper);
        for (ServiceDo service : serviceDoList) {
            serviceDo.setDataTraffic(serviceDo.getDataTraffic() + service.getDataTraffic());
        }
        int year = DateUtil.year(new Date());
        int month = DateUtil.month(new Date()) + 1;
        List<AladdinNodeVmess> aladdinNodeVmessList = aladdinNodeVmessService.listByServiceId(serviceDo.getId(), year, month);
        long up = 0L;
        long down = 0L;
        for (AladdinNodeVmess aladdinNodeVmess : aladdinNodeVmessList) {
            up += aladdinNodeVmess.getServiceUp() * aladdinNodeVmess.getMultiplyingPower();
            down += aladdinNodeVmess.getServiceDown() * aladdinNodeVmess.getMultiplyingPower();
        }
        Double total = ((double) (up + down) / 1024 / 1024 / 1024);
        String surplus = String.format("%.2f", serviceDo.getDataTraffic() - total);
        serviceDo.setSurplus(surplus);
    }
}
