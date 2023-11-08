package com.yeem.lamp.service.impl;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.UUID;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.lamp.entity.*;
import com.yeem.lamp.mapper.ServerMapper;
import com.yeem.lamp.service.*;
import com.yeem.common.utils.FreeMakerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    @DS("proxy")
    @Override
    public void refreshXray() {
        List<Server> serverList = serverMapper.selectList(new QueryWrapper<>());
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
        memberService.calculateData();

        // 4、生成config.json配置文件 重启
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("end", LocalDateTime.now()).or().lt("traffic_surplus_month", 0);
        List<Member> memberList = memberService.list(queryWrapper);
        List<Long> invalidMemberIdList = memberList.stream().map(Member::getId).collect(Collectors.toList());

        List<Node> nodeList = nodeService.list(new QueryWrapper<>());

        for (Server server : serverList) {
            List<Map<String, Object>> clientList = new ArrayList<>();
            nodeList.forEach(node -> {
                if (!invalidMemberIdList.contains(node.getMemberId())) {
                    Map<String, Object> client = new HashMap<>();
                    client.put("id", node.getUuid());
                    client.put("flow", "xtls-rprx-direct");
                    client.put("level", 1);
                    client.put("email", cn.hutool.core.codec.Base64.encode(node.getMemberId().toString().getBytes(StandardCharsets.UTF_8)).replace("=", "") + "@" + server.getXrayDomain());
                    clientList.add(client);
                }
            });
            try {
                // 数据模型
                Map<String, Object> map = new HashMap<>();
                ObjectMapper objectMapper = new ObjectMapper();
                map.put("clients", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientList));
                map.put("xray_domain", server.getXrayDomain());
                map.put("xray_access_log", server.getXrayAccessLog());
                map.put("xray_error_log", server.getXrayErrorLog());
                map.put("xray_ws_path", server.getXrayWsPath());
                map.put("xray_certificate_file", server.getXrayCertificateFile());
                map.put("xray_key_file", server.getXrayKeyFile());
                String content = FreeMakerUtils.getContent("/data/yeem/config/template/", "xray_config.ftl", map);
                String uuid = UUID.fastUUID().toString();
                String basePath = "/usr/snail/webapps/file/config/";
                String baseUrl = "https://yeemcloud.com/file/config/";
                String relativePath = uuid + "/config.json";
                FileWriter fileWriter = new FileWriter(basePath + relativePath);
                fileWriter.write(content);
                CommandRecord commandRecord = new CommandRecord();
                commandRecord.setIp(server.getXrayDomain());
                commandRecord.setFlag(-1);
                StringBuilder command = new StringBuilder();
                command.append("wget ").append(baseUrl).append(relativePath).append(" -O /usr/local/etc/xray/config.json;");
                command.append("\n");
                command.append("systemctl restart xray");
                command.append("\n");
                commandRecord.setCommand(command.toString());
                commandRecord.setType("xray");
                commandRecordService.save(commandRecord);
            } catch (Exception e) {
                log.error("解析模版异常:", e);
            }
        }
    }
}
