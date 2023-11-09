package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.lamp.entity.AladdinNodeVmess;
import com.yeem.lamp.mapper.AladdinNodeVmessMapper;
import com.yeem.lamp.service.IAladdinNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AladdinNodeServiceImpl implements IAladdinNodeService {

    public static final String NODE_TYPE_PRIVATE = "private";
    public static final String NODE_TYPE_PUBLIC = "public";

    @Autowired
    private AladdinNodeVmessMapper aladdinNodeVmessMapper;

    @Override
    public List<String> getNodeUrlList(String uuid) {
        List<String> nodeUrlList = new ArrayList<>();
        QueryWrapper<AladdinNodeVmess> privateAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
        privateAladdinNodeVmessQueryWrapper.eq("node_id", uuid);
        List<AladdinNodeVmess> privateAaladdinNodeVmessList = aladdinNodeVmessMapper.selectList(privateAladdinNodeVmessQueryWrapper);
        for (AladdinNodeVmess aladdinNodeVmess : privateAaladdinNodeVmessList) {
            nodeUrlList.add(aladdinNodeVmess.convert());
        }
        QueryWrapper<AladdinNodeVmess> publicAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
        publicAladdinNodeVmessQueryWrapper.eq("node_type", "public");
        List<AladdinNodeVmess> publicAaladdinNodeVmessList = aladdinNodeVmessMapper.selectList(publicAladdinNodeVmessQueryWrapper);
        for (AladdinNodeVmess aladdinNodeVmess : publicAaladdinNodeVmessList) {
            nodeUrlList.add(aladdinNodeVmess.convert());
        }
        return nodeUrlList;
    }
}
