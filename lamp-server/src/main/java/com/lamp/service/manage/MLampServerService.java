package com.lamp.service.manage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampServer;
import com.lamp.mapper.LampServerMapper;
import com.lamp.xui.XClient;
import com.lamp.xui.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Slf4j
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
        return super.save(entity);
    }

    @Override
    public boolean updateById(LampServer entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    public void syncToLocal(LampServer server) {
        server = getById(server.getId());
        XClient xClient = XClient.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
        List<XInbound> xInboundList = xClient.getInbound();
        List<LampInbound> inboundList = LampInbound.batchConvert(xInboundList);
        if (Objects.isNull(inboundList) || inboundList.isEmpty()) {
            return;
        }
        for (LampInbound inbound : inboundList) {
            inbound.setServerId(server.getId());
            for (LampInbound oldInbound : server.getInboundList()) {
                if (Objects.equals(inbound.getInboundId(), oldInbound.getInboundId())) {
                    inbound.setId(oldInbound.getId());
                    inbound.setMultiplyingPower(oldInbound.getMultiplyingPower());
                }
            }
        }
        inboundService.saveOrUpdateBatch(inboundList);
        log.info("获取到服务器 inbound: {}", inboundList);
    }
}
