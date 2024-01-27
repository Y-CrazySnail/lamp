package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinNodeVmess;
import com.yeem.lamp.entity.AladdinServer;
import com.yeem.lamp.mapper.AladdinNodeVmessMapper;
import com.yeem.lamp.service.IAladdinNodeVmessService;
import com.yeem.lamp.service.IAladdinServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AladdinNodeVmessServiceImpl extends ServiceImpl<AladdinNodeVmessMapper, AladdinNodeVmess> implements IAladdinNodeVmessService {

    public static final String NODE_TYPE_PRIVATE = "private";
    public static final String NODE_TYPE_PUBLIC = "public";

    @Autowired
    private AladdinNodeVmessMapper aladdinNodeVmessMapper;
    @Autowired
    private IAladdinServerService aladdinServerService;

    @Override
    public List<String> getNodeUrlList(String uuid, String label) {
        List<String> nodeUrlList = new ArrayList<>();
        QueryWrapper<AladdinNodeVmess> privateAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
        privateAladdinNodeVmessQueryWrapper.eq("node_id", uuid);
        List<AladdinNodeVmess> privateAaladdinNodeVmessList = aladdinNodeVmessMapper.selectList(privateAladdinNodeVmessQueryWrapper);
        for (AladdinNodeVmess aladdinNodeVmess : privateAaladdinNodeVmessList) {
            nodeUrlList.add(aladdinNodeVmess.convert());
        }
        if (!StringUtils.isEmpty(label) && label.contains("whatsapp")) {
            QueryWrapper<AladdinNodeVmess> whatsappAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
            whatsappAladdinNodeVmessQueryWrapper.eq("node_type", "whatsapp");
            List<AladdinNodeVmess> whatsappAaladdinNodeVmessList = aladdinNodeVmessMapper.selectList(whatsappAladdinNodeVmessQueryWrapper);
            for (AladdinNodeVmess aladdinNodeVmess : whatsappAaladdinNodeVmessList) {
                nodeUrlList.add(aladdinNodeVmess.convert());
            }
        }
        QueryWrapper<AladdinNodeVmess> publicAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
        publicAladdinNodeVmessQueryWrapper.eq("node_type", "public");
        List<AladdinNodeVmess> publicAaladdinNodeVmessList = aladdinNodeVmessMapper.selectList(publicAladdinNodeVmessQueryWrapper);
        for (AladdinNodeVmess aladdinNodeVmess : publicAaladdinNodeVmessList) {
            nodeUrlList.add(aladdinNodeVmess.convert());
        }
        return nodeUrlList;
    }

    @Override
    public List<AladdinNodeVmess> listByServerId(Long serverId, int year, int month) {
        QueryWrapper<AladdinNodeVmess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("server_id", serverId);
        queryWrapper.eq("service_year", year);
        queryWrapper.eq("service_month", month);
        queryWrapper.ne("node_type", "expired");
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public List<AladdinNodeVmess> listByServiceId(Long serviceId, int year, int month) {
        QueryWrapper<AladdinNodeVmess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_id", serviceId);
        queryWrapper.eq("service_year", year);
        queryWrapper.eq("service_month", month);
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.orderByAsc("sort");
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public List<AladdinNodeVmess> listByNodeType(String nodeType) {
        QueryWrapper<AladdinNodeVmess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("node_type", nodeType);
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updateByServerId(Long serverId, String nodeType, String nodePs, Integer sort) {
        AladdinServer aladdinServer = aladdinServerService.getById(serverId);
        UpdateWrapper<AladdinNodeVmess> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("multiplying_power", aladdinServer.getMultiplyingPower());
        if (!StringUtils.isEmpty(nodePs)) {
            updateWrapper.set("node_ps", nodePs);
        }
        if (!StringUtils.isEmpty(nodeType)) {
            updateWrapper.set("node_type", nodeType);
        }
        if (!StringUtils.isEmpty(sort)) {
            updateWrapper.set("sort", sort);
        }
        updateWrapper.eq("server_id", serverId);
        return super.update(updateWrapper);
    }
}
