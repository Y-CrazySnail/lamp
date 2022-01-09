package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Member;
import com.snail.entity.Node;
import com.snail.mapper.MemberMapper;
import com.snail.mapper.NodeMapper;
import com.snail.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements INodeService {

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
}
