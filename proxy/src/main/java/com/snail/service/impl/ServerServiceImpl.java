package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.entity.CommandRecord;
import com.snail.entity.Member;
import com.snail.entity.Server;
import com.snail.entity.Traffic;
import com.snail.mapper.ServerMapper;
import com.snail.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements IServerService {

    @Autowired
    private ServerMapper serverMapper;
    @Autowired
    private ICommandRecordService commandRecordService;
    @Autowired
    private ITrafficService trafficService;
    @Autowired
    private INodeService nodeService;
    @Autowired
    private IMemberService memberService;

    @Override
    public void refreshXray() {
        List<Server> serverList = serverMapper.selectList(new QueryWrapper<>());
        serverList = serverList.stream().filter(item -> !item.getIp().equals("vhfugv.buzz")).collect(Collectors.toList());
        for (Server server : serverList) {
            String uuid = cn.hutool.core.lang.UUID.fastUUID().toString();

            // 1、服务器生成流量使用情况文件
            CommandRecord statusCommandRecord = new CommandRecord();
            statusCommandRecord.setIp(server.getIp());
            statusCommandRecord.setFlag(-1);
            statusCommandRecord.setCommand("xray api statsquery --server=127.0.0.1:10085  'pattern: \"\" reset: true' > /usr/local/etc/xray/" + uuid + ".rich;");
            statusCommandRecord.setType("xray");
            commandRecordService.save(statusCommandRecord);
            log.info("statusCommandRecord id: {}", statusCommandRecord.getId());

            while (statusCommandRecord.getFlag() != 1) {
                statusCommandRecord = commandRecordService.getById(statusCommandRecord.getId());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 2、获取流量使用情况文件内容
            CommandRecord catCommandRecord = new CommandRecord();
            catCommandRecord.setIp(server.getIp());
            catCommandRecord.setFlag(-1);
            catCommandRecord.setCommand("cat /usr/local/etc/xray/" + uuid + ".rich;");
            catCommandRecord.setType("xray");
            commandRecordService.save(catCommandRecord);
            log.info("catCommandRecord result: {}", catCommandRecord.getResult());

            while (catCommandRecord.getFlag() != 1) {
                catCommandRecord = commandRecordService.getById(catCommandRecord.getId());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 3、计算流量
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = null;
            try {
                map = objectMapper.readValue(catCommandRecord.getResult(), new TypeReference<Map<String, Object>>() {
                });
                if (!StringUtils.isEmpty(map.get("stat"))) {
                    List<Map<String, Object>> infoList = (List<Map<String, Object>>) map.get("stat");
                    infoList.forEach(info -> {
                        String name = (String) info.get("name");
                        if (name.contains("@")) {
                            String id = new String(org.apache.commons.codec.binary.Base64.decodeBase64(name.split(">>>")[1].split("@")[0]));
                            String type = name.split(">>>")[3];
                            Long value = Long.valueOf(String.valueOf(info.get("value")));
                            Traffic traffic = new Traffic();
                            traffic.setTraffic(value);
                            traffic.setMemberId(Long.valueOf(id));
                            traffic.setType(type);
                            traffic.setFlag(0);
                            trafficService.save(traffic);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        calculateTraffic();
        // 4、生成config.json配置文件 重启
        nodeService.refresh();
    }

    private void calculateTraffic() {
        QueryWrapper<Traffic> trafficQueryWrapper = new QueryWrapper<>();
        trafficQueryWrapper.eq("flag", 0);
        List<Traffic> trafficList = trafficService.list(trafficQueryWrapper);
        trafficList.forEach(traffic -> {
            Member member = memberService.getById(traffic.getMemberId());
            if (member != null) {
                if (traffic.getType().equals("downlink")) {
                    member.setTrafficDownMonth(member.getTrafficDownMonth() + traffic.getTraffic());
                }
                if (traffic.getType().equals("uplink")) {
                    member.setTrafficUpMonth(member.getTrafficUpMonth() + traffic.getTraffic());
                }
                member.setTrafficSurplusMonth(member.getTrafficSurplusMonth() - traffic.getTraffic());
                memberService.updateById(member);
            }
            traffic.setFlag(1);
            trafficService.updateById(traffic);
        });
    }
}
