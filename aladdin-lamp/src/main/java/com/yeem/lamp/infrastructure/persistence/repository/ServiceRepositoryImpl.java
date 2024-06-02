package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yeem.lamp.domain.entity.Service;
import com.yeem.lamp.domain.repository.ServiceRepository;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    @Autowired
    private ServiceMapper serviceMapper;

    /**
     * 根据会员ID查询服务列表
     *
     * @param memberId 会员ID
     * @return 服务列表
     */
    @Override
    public List<Service> listByMemberId(Long memberId) {
        LambdaQueryWrapper<ServiceDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceDo::getMemberId, memberId);
        queryWrapper.eq(ServiceDo::getDeleteFlag, false);
        List<ServiceDo> serviceDoList = serviceMapper.selectList(queryWrapper);
        return serviceDoList.stream().map(ServiceDo::convertService).collect(Collectors.toList());
    }
}
