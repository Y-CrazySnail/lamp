package com.yeem.lamp.infrastructure.x;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yeem.lamp.domain.entity.NodeVmess;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.infrastructure.x.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Data
@Slf4j
public class XUIClient {

    /**
     * 地址
     */
    private String host;
    /**
     * 端口
     */
    private int port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录缓存
     */
    private HttpCookie cookie;
    /**
     * 过期时间
     */
    private Date expireDate;

    /**
     * cookie过期判断
     *
     * @return true已过期 false未过期
     */
    public boolean expired() {
        return this.expireDate.before(new Date());
    }

    public static Map<String, XUIClient> XUI_CLIENT_MAP = new ConcurrentHashMap<>();

    public static XUIClient init(Server server) {
        if (XUI_CLIENT_MAP.containsKey(server.getApiIp())) {
            XUIClient xuiClient = XUI_CLIENT_MAP.get(server.getApiIp());
            if (!xuiClient.expired()) {
                return xuiClient;
            } else {
                XUI_CLIENT_MAP.remove(server.getApiIp());
            }
        }
        XUIClient xuiClient = login(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
        XUI_CLIENT_MAP.put(server.getApiIp(), xuiClient);
        return xuiClient;
    }

    private static XUIClient login(String host, int port, String username, String password) {
        ObjectMapper objectMapper = new ObjectMapper();
        XLogin xLogin = new XLogin();
        xLogin.setUsername(username);
        xLogin.setPassword(password);
        HttpResponse response;
        try {
            response = HttpRequest.post("http://" + host + ":" + port + XUIApi.LOGIN)
                    .body(objectMapper.writeValueAsString(xLogin))
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            XResponse<Object> xResponse = objectMapper.readValue(response.body(), new TypeReference<XResponse<Object>>() {
            });
            if (null != xResponse && xResponse.isSuccess()) {
                XUIClient xuiClient = new XUIClient();
                xuiClient.setHost(host);
                xuiClient.setPort(port);
                xuiClient.setUsername(username);
                xuiClient.setPassword(password);
                xuiClient.setCookie(response.getCookie("session"));
                xuiClient.setExpireDate(DateUtil.offsetDay(new Date(), 30));
                return xuiClient;
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<XInbound> getInboundList() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.INBOUND_LIST)
                .cookie(this.cookie)
                .execute();
        String body = response.body();
        try {
            XResponse<List<XInbound>> xResponse = objectMapper.readValue(body, new TypeReference<XResponse<List<XInbound>>>() {
            });
            if (xResponse.isSuccess()) {
                return xResponse.getObj();
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void delInbound() {
        List<XInbound> xInboundList = getInboundList();
        for (XInbound xInbound : xInboundList) {
            String path = String.format(XUIApi.INBOUND_DEL, xInbound.getId());
            HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + path)
                    .cookie(this.cookie)
                    .execute();
        }
    }

    public void addVmessInbound(Server server, List<NodeVmess> nodeVmessList) {
        ObjectMapper objectMapper = new ObjectMapper();
        XInbound xInbound = new XInbound();
        xInbound.setUp(0);
        xInbound.setDown(0);
        xInbound.setTotal(0);
        xInbound.setRemark(server.getNodeRemark());
        xInbound.setEnable(true);
        xInbound.setExpiryTime(0);
        xInbound.setListen(null);
        xInbound.setPort(server.getNodePort());
        xInbound.setProtocol("vmess");
        xInbound.initSteamSettings();
        xInbound.initSniffing();
        List<XVmessClient> xVmessClientList = nodeVmessList.stream().map(XVmessClient::new).collect(Collectors.toList());
        xInbound.initVmessSetting(xVmessClientList);
        Map<String, Object> formData = BeanUtil.beanToMap(xInbound);
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.INBOUND_ADD)
                .cookie(this.cookie)
                .form(formData)
                .execute();
    }

    public void addVmessClient(XInbound inbound, NodeVmess nodeVmess) {
        XInbound xInbound = new XInbound();
        xInbound.setId(inbound.getId());
        XVmessClient xVmessClient = new XVmessClient(nodeVmess);
        List<XVmessClient> xVmessClientList = Collections.singletonList(xVmessClient);
        xInbound.initVmessSetting(xVmessClientList);
        Map<String, Object> formData = BeanUtil.beanToMap(xInbound);
        HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.CLIENT_ADD)
                .cookie(this.cookie)
                .form(formData)
                .execute();
    }

    public void delVmessClient(NodeVmess nodeVmess) {
        XVmessClient xVmessClient = new XVmessClient(nodeVmess);
        List<XInbound> xInboundList = getInboundList();
        for (XInbound xInbound : xInboundList) {
            String path = String.format(XUIApi.CLIENT_DEL, xInbound.getId(), xVmessClient.getId());
            HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + path)
                    .cookie(this.cookie)
                    .execute();
            log.info("{}", response);
        }
    }

    public void delVmessClient(int xInboundId, int xVmessClientId) {
        String path = String.format(XUIApi.CLIENT_DEL, xInboundId, xVmessClientId);
        HttpRequest.post("http://" + this.host + ":" + this.port + path)
                .cookie(this.cookie)
                .execute();
    }

    public Object getInboundOnline() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.INBOUND_ONLINE)
                .cookie(this.cookie)
                .execute();
        String body = response.body();
        try {
            // String 对应 email 字段
            XResponse<List<String>> xResponse = objectMapper.readValue(body, new TypeReference<XResponse<List<String>>>() {
            });
            log.info("{}", xResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public XSystemStat getServerStatus() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.SERVER_STATUS)
                .cookie(this.cookie)
                .execute();
        try {
            XResponse<XSystemStat> xResponse = objectMapper.readValue(response.body(), new TypeReference<XResponse<XSystemStat>>() {
            });
            if (xResponse.isSuccess()) {
                return xResponse.getObj();
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.setApiIp("107.6.243.224");
        server.setApiPort(9999);
        server.setApiUsername("aladdin");
        server.setApiPassword("aladdin");
        server.setNodePort(20000);
        server.setNodeRemark("ALADDIN");
        XUIClient xuiClient = XUIClient.init(server);
//        xuiClient.delInbound("5");
        xuiClient.getInboundList();
//        xuiClient.getInboundOnline();
        NodeVmess nodeVmess = new NodeVmess();
        nodeVmess.setNodeId("1ba763c5-8e76-4e2d-974f-f6b6292cc5f4");
//        nodeVmess.setNodeId(UUID.fastUUID().toString());
        nodeVmess.setServiceDate(new Date());
        nodeVmess.setServerId(1L);
        nodeVmess.setServiceId(3L);
//        List<NodeVmess> nodeVmessList = new ArrayList<>();
//        nodeVmessList.add(nodeVmess);
//        xuiClient.addVmessInbound(server, nodeVmessList);
//        xuiClient.addVmessClient(nodeVmess);
        xuiClient.getServerStatus();
    }
}
