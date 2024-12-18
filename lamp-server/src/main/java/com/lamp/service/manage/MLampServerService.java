package com.lamp.service.manage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampServer;
import com.lamp.entity.LampServiceMonth;
import com.lamp.mapper.LampServerMapper;
import com.lamp.xui.XClient;
import com.lamp.xui.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MLampServerService extends ServiceImpl<LampServerMapper, LampServer> {

    @Autowired
    private MLampInboundService inboundService;

    @Autowired
    private MLampServiceMonthService serviceMonthService;

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
        super.updateById(entity);
        XClient xClient = XClient.init(entity.getApiIp(), entity.getApiPort(), entity.getApiUsername(), entity.getApiPassword());
        List<XInbound> xInboundList = xClient.getInbound();
        List<LampInbound> remoteInboundList = LampInbound.batchConvert(xInboundList);
        List<LampInbound> localInboundList = entity.getInboundList();
        // 处理远程存在、本地不存在的 inbound
        for (LampInbound remoteInbound : remoteInboundList) {
            boolean flag = true;
            for (LampInbound localInbound : localInboundList) {
                if (Objects.equals(remoteInbound.getInboundPort(), localInbound.getInboundPort())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                log.info("服务器:{},入站端口:{},远程存在，本地不存在，远程执行删除操作", entity.getApiIp(), remoteInbound.getInboundPort());
                xClient.delInbound(remoteInbound.getInboundId());
            }
        }
        // 处理远程不存在、本地存在的 inbound
        for (LampInbound localInbound : localInboundList) {
            boolean flag = true;
            for (LampInbound remoteInbound : remoteInboundList) {
                if (Objects.equals(localInbound.getInboundPort(), remoteInbound.getInboundPort())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                log.info("服务器:{},入站端口:{},远程不存在，本地存在，远程执行新增操作", entity.getApiIp(), localInbound.getInboundPort());
                List<LampServiceMonth> serviceMonthList = serviceMonthService.list(new Date());
                xClient.addInbound(localInbound.getInboundPort(), "vmess", localInbound.getRemark(), new ArrayList<>());
            }
        }
        return true;
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
