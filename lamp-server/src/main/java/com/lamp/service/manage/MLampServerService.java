package com.lamp.service.manage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.entity.LampServer;
import com.lamp.mapper.LampServerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;

@Service
public class MLampServerService extends ServiceImpl<LampServerMapper, LampServer> {

    @Autowired
    private MLampInboundService inboundService;

    @Override
    public LampServer getById(Serializable id) {
        LampServer server = super.getById(id);
        inboundService.setInboundList(server);
        return server;
    }

    @Override
    public boolean save(LampServer entity) {
        super.save(entity);
        if (Objects.nonNull(entity.getInboundList()) && !entity.getInboundList().isEmpty()) {
            entity.getInboundList().forEach(inbound -> inbound.setServerId(entity.getId()));
            inboundService.saveBatch(entity.getInboundList());
        }
        return true;
    }

    @Override
    public boolean updateById(LampServer entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
