package com.yeem.lamp.xui;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class XUIRequest {
    private static final String DEFAULT_REMARK = "ALADDIN";

    private ObjectMapper objectMapper = new ObjectMapper();
    private String ip;
    private int port;
    private String remark;
    private HttpCookie cookie;
    private XUIInboundData xuiInboundData;

    public static XUIRequest request() {
        return new XUIRequest();
    }

    public XUIRequest ip(String ip) {
        this.ip = ip;
        return this;
    }

    public XUIRequest port(int port) {
        this.port = port;
        return this;
    }

    public XUIRequest remark(String remark) {
        this.remark = remark;
        return this;
    }

    public XUIRequest login(String username, String password) throws IOException {
        XUILogin xuiLogin = new XUILogin();
        xuiLogin.setUsername(username);
        xuiLogin.setPassword(password);
        HttpResponse response = HttpRequest.post("http://" + this.ip + ":" + this.port + "/login")
                .body(objectMapper.writeValueAsString(xuiLogin))
                .execute();
        XUIResponse xuiResponse = objectMapper.readValue(response.body(), XUIResponse.class);
        if (!StringUtils.isEmpty(xuiResponse) && xuiResponse.isSuccess()) {
            this.cookie = response.getCookie("session");
        } else {
            log.error(xuiResponse.getMsg());
        }
        return this;
    }

    public XUIInboundData sync(List<XUIVmessClient> vmessClientList) throws IOException {
        list();
        if (Objects.isNull(xuiInboundData)) {
            log.error("当前服务器未创建[备注]为ALADDIN的Vmess入口：{},开始创建--------------->", this.ip);
            Map<String, Object> formData = new HashMap<>();
            formData.put("up", 0);
            formData.put("down", 0);
            formData.put("total", 0);
            formData.put("remark", "ALADDIN");
            formData.put("enable", true);
            formData.put("expiryTime", 0);
            formData.put("listen", null);
            formData.put("port", 20000);
            formData.put("protocol", "vmess");
            List<String> clientStrList = new ArrayList<>();
            for (XUIVmessClient xuiVmessClient : vmessClientList) {
                clientStrList.add(xuiVmessClient.getString());
            }
            formData.put("settings", "{\n" +
                    "  \"clients\": [\n" +
                    String.join(",", clientStrList) +
                    "  ]\n" +
                    "}");
            log.info("{}", String.join(",", clientStrList));
            formData.put("streamSettings", "{\n" +
                    "  \"network\": \"tcp\",\n" +
                    "  \"security\": \"none\",\n" +
                    "  \"externalProxy\": [],\n" +
                    "  \"tcpSettings\": {\n" +
                    "    \"acceptProxyProtocol\": false,\n" +
                    "    \"header\": {\n" +
                    "      \"type\": \"none\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}");
            formData.put("sniffing", "{\n" +
                    "  \"enabled\": true,\n" +
                    "  \"destOverride\": [\n" +
                    "    \"http\",\n" +
                    "    \"tls\",\n" +
                    "    \"quic\",\n" +
                    "    \"fakedns\"\n" +
                    "  ]\n" +
                    "}");
            HttpResponse addResponse = HttpRequest.post("http://" + this.ip + ":" + this.port + "/xui/inbound/add")
                    .cookie(this.cookie)
                    .form(formData)
                    .execute();
            XUIResponse xuiResponseAdd = objectMapper.readValue(addResponse.body(), XUIResponse.class);
            if (!StringUtils.isEmpty(xuiResponseAdd) && xuiResponseAdd.isSuccess()) {
                log.error("当前服务器未创建[备注]为ALADDIN的Vmess入口：{},结束创建<---------------", this.ip);
                list();
            } else {
                log.error("{}", xuiResponseAdd);
            }
        } else {
            Map<String, List<XUIInboundData.Client>> xuiInboundDataListMap = objectMapper.readValue(xuiInboundData.getSettings(), new TypeReference<Map<String, List<XUIInboundData.Client>>>() {
            });
            List<XUIInboundData.Client> remoteClientList = xuiInboundDataListMap.get("clients");
            for (XUIInboundData.Client client : remoteClientList) {
                long count = vmessClientList.stream()
                        .filter(e -> e.getEmail().equals(client.getEmail()) && e.getId().equals(client.getId()))
                        .count();
                if (count == 0) {
                    log.info("当前远端节点已过期，删除远端节点信息：clientId:{}", client.getId());
                    HttpRequest.post("http://" + this.ip + ":" + this.port + "/xui/inbound/" + xuiInboundData.getId() + "/delClient/" + client.getId())
                            .cookie(this.cookie)
                            .execute();
                }
            }
            for (XUIVmessClient client : vmessClientList) {
                long count = remoteClientList.stream()
                        .filter(e -> e.getEmail().equals(client.getEmail()) && e.getId().equals(client.getId()))
                        .count();
                if (count == 0) {
                    log.info("当前远端不存在本地节点，新增至远端节点信息列表：{}", client.getId());
                    HttpRequest.post("http://" + this.ip + ":" + this.port + "/xui/inbound/addClient")
                            .cookie(this.cookie)
                            .form("id", xuiInboundData.getId())
                            .form("settings", "{\n" +
                                    "  \"clients\": [\n" +
                                    client.getString() +
                                    "  ]}\n")
                            .execute();
                }
            }
            list();
        }
        return xuiInboundData;
    }

    private void list() throws IOException {
        HttpResponse response = HttpRequest.post("http://" + this.ip + ":" + this.port + "/xui/inbound/list")
                .cookie(this.cookie)
                .execute();
        XUIResponse xuiResponse = objectMapper.readValue(response.body(), XUIResponse.class);
        if (!StringUtils.isEmpty(xuiResponse) && xuiResponse.isSuccess()) {
            String objStr = objectMapper.writeValueAsString(xuiResponse.getObj());
            List<XUIInboundData> xuiInboundDataList = objectMapper.readValue(objStr, new TypeReference<List<XUIInboundData>>() {
            });
            for (XUIInboundData xuiInboundData : xuiInboundDataList) {
                if (!StringUtils.isEmpty(xuiInboundData) && this.remark.equals(xuiInboundData.getRemark())) {
                    this.xuiInboundData = xuiInboundData;
                    break;
                }
            }
        } else {
            log.error(xuiResponse.getMsg());
        }
    }
}
