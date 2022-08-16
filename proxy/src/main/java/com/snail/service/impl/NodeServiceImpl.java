package com.snail.service.impl;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.entity.*;
import com.snail.mapper.MemberMapper;
import com.snail.mapper.NodeMapper;
import com.snail.mapper.ServerMapper;
import com.snail.service.ICommandRecordService;
import com.snail.service.INodeService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements INodeService {

    @Autowired
    private ICommandRecordService commandRecordService;

    String template = "{\n" +
            "    \"log\": {\n" +
            "        \"access\": \"/var/log/v2ray/access.log\",\n" +
            "        \"error\": \"/var/log/v2ray/error.log\",\n" +
            "        \"loglevel\": \"warning\"\n" +
            "    },\n" +
            "    \"routing\": {\n" +
            "        \"rules\": [\n" +
            "            {\n" +
            "                \"inboundTag\": [\n" +
            "                    \"api\"\n" +
            "                ],\n" +
            "                \"outboundTag\": \"api\",\n" +
            "                \"type\": \"field\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"ip\": [\n" +
            "                    \"geoip:private\"\n" +
            "                ],\n" +
            "                \"outboundTag\": \"blocked\",\n" +
            "                \"type\": \"field\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"outboundTag\": \"blocked\",\n" +
            "                \"protocol\": [\n" +
            "                    \"bittorrent\"\n" +
            "                ],\n" +
            "                \"type\": \"field\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    \"dns\": null,\n" +
            "    \"inbounds\": [\n" +
            "        {\n" +
            "            \"listen\": \"127.0.0.1\",\n" +
            "            \"port\": 10085,\n" +
            "            \"protocol\": \"dokodemo-door\",\n" +
            "            \"settings\": {\n" +
            "                \"address\": \"127.0.0.1\"\n" +
            "            },\n" +
            "            \"streamSettings\": null,\n" +
            "            \"tag\": \"api\",\n" +
            "            \"sniffing\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"listen\": null,\n" +
            "            \"port\": 443,\n" +
            "            \"protocol\": \"vless\",\n" +
            "            \"settings\": {\n" +
            "                \"clients\": [$clients],\n" +
            "                \"decryption\": \"none\",\n" +
            "                \"fallbacks\": []\n" +
            "            },\n" +
            "            \"streamSettings\": {\n" +
            "                \"network\": \"ws\",\n" +
            "                \"security\": \"tls\",\n" +
            "                \"tlsSettings\": {\n" +
            "                    \"serverName\": \"vhfugv.buzz\",\n" +
            "                    \"certificates\": [\n" +
            "                        {\n" +
            "                            \"certificateFile\": \"/etc/nginx/vhfugvbuzz/fullchain.cer\",\n" +
            "                            \"keyFile\": \"/etc/nginx/vhfugvbuzz/vhfugv.buzz.key\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                \"wsSettings\": {\n" +
            "                    \"path\": \"/c5fa7e2466516a1/\",\n" +
            "                    \"headers\": {}\n" +
            "                }\n" +
            "            },\n" +
            "            \"tag\": \"inbound-443\",\n" +
            "            \"sniffing\": {\n" +
            "                \"enabled\": true,\n" +
            "                \"destOverride\": [\n" +
            "                    \"http\",\n" +
            "                    \"tls\"\n" +
            "                ]\n" +
            "            }\n" +
            "        }\n" +
            "    ],\n" +
            "    \"outbounds\": [\n" +
            "        {\n" +
            "            \"protocol\": \"freedom\",\n" +
            "            \"settings\": {}\n" +
            "        },\n" +
            "        {\n" +
            "            \"protocol\": \"blackhole\",\n" +
            "            \"settings\": {},\n" +
            "            \"tag\": \"blocked\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"transport\": null,\n" +
            "    \"policy\": {\n" +
            "        \"system\": {\n" +
            "            \"statsInboundUplink\": false,\n" +
            "            \"statsInboundDownlink\": false,\n" +
            "            \"statsOutboundUplink\": false,\n" +
            "            \"statsOutboundDownlink\": false\n" +
            "        },\n" +
            "        \"levels\": {\n" +
            "            \"1\": {\n" +
            "                \"statsUserUplink\": true,\n" +
            "                \"statsUserDownlink\": true\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    \"api\": {\n" +
            "        \"services\": [\n" +
            "            \"HandlerService\",\n" +
            "            \"LoggerService\",\n" +
            "            \"StatsService\"\n" +
            "        ],\n" +
            "        \"tag\": \"api\"\n" +
            "    },\n" +
            "    \"stats\": {},\n" +
            "    \"reverse\": null,\n" +
            "    \"fakeDns\": null\n" +
            "}";

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ServerMapper serverMapper;

    @Override
    public String getConfiguration() {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("end", LocalDateTime.now()).or().lt("traffic_surplus_month", 0);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        List<Long> invalidMemberIdList = memberList.stream().map(Member::getId).collect(Collectors.toList());
        List<Node> nodeList = nodeMapper.selectList(new QueryWrapper<>());
        StringBuilder stringBuilder = new StringBuilder("\n");
        nodeList.forEach(node -> {
            if (!invalidMemberIdList.contains(node.getMemberId())) {
                stringBuilder.append("                    {\"id\": \"")
                        .append(node.getUuid())
                        .append("\",")
                        .append("\"flow\": \"xtls-rprx-direct\",\"level\": 1,\"email\": \"")
                        .append(Base64.getEncoder().encodeToString(String.valueOf(node.getMemberId()).getBytes()).replace("=", "").replace("=", "")).append("@vhfugv.com\"},\n");
            }
        });
        String clients = stringBuilder.substring(0, stringBuilder.length() - 2);
        return template.replace("$clients", clients);
    }

    @Override
    public String refresh() {
        List<Server> serverList = serverMapper.selectList(new QueryWrapper<>());

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("end", LocalDateTime.now()).or().lt("traffic_surplus_month", 0);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        List<Long> invalidMemberIdList = memberList.stream().map(Member::getId).collect(Collectors.toList());

        List<Node> nodeList = nodeMapper.selectList(new QueryWrapper<>());

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
                // 创建配置类
                Configuration configuration = new Configuration(Configuration.getVersion());
                configuration.setDirectoryForTemplateLoading(new File("/usr/snail/config/template/"));
                // 设置字符集
                configuration.setDefaultEncoding("utf-8");
                // 加载模板
                Template template = configuration.getTemplate("xray_config.ftl");
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
                String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
                String uuid = UUID.fastUUID().toString();
                String basePath = "/usr/snail/webapps/file/config/";
                String baseUrl = "https://edreamroom.com/file/config/";
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
                commandRecord.setType("test");
                commandRecordService.save(commandRecord);
            } catch (Exception e) {
                log.error("解析模版异常:", e);
            }
        }
        return "";
    }
}
