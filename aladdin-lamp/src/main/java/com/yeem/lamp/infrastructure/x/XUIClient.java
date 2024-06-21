package com.yeem.lamp.infrastructure.x;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.infrastructure.x.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
     * 备注
     */
    private String remark;
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

    public static Map<Long, XUIClient> XUI_CLIENT_MAP = new ConcurrentHashMap<>();

    public static XUIClient init(Server server) {
        if (XUI_CLIENT_MAP.containsKey(server.getId())) {
            XUIClient xuiClient = XUI_CLIENT_MAP.get(server.getId());
            if (!xuiClient.expired()) {
                return xuiClient;
            } else {
                XUI_CLIENT_MAP.remove(server.getId());
            }
        }
        XUIClient xuiClient = login(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword(), server.getId());
        XUI_CLIENT_MAP.put(server.getId(), xuiClient);
        return xuiClient;
    }

    private static XUIClient login(String host, int port, String username, String password, Long serverId) {
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
                xuiClient.setRemark(String.valueOf(serverId));
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

    public XInbound getInbound() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.INBOUND_LIST)
                .cookie(this.cookie)
                .execute();
        String body = response.body();
        try {
            XResponse<List<XInbound>> xResponse = objectMapper.readValue(body, new TypeReference<XResponse<List<XInbound>>>() {
            });
            if (xResponse.isSuccess()) {
                for (XInbound xInbound : xResponse.getObj()) {
                    if (xInbound.getRemark().equals(String.valueOf(this.remark))) {
                        return xInbound;
                    } else {
                        return null;
                    }
                }
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return null;
    }

    public void delInbound() {
        XInbound xInbound = getInbound();
        String path = String.format(XUIApi.INBOUND_DEL, xInbound.getId());
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + path)
                .cookie(this.cookie)
                .execute();
    }

    public void addVmessInbound(Long serverId, int inboundPort, Map<Long, String> serviceMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        XInbound xInbound = new XInbound();
        xInbound.setUp(0);
        xInbound.setDown(0);
        xInbound.setTotal(0);
        xInbound.setRemark(String.valueOf(serverId));
        xInbound.setEnable(true);
        xInbound.setExpiryTime(0);
        xInbound.setListen(null);
        xInbound.setPort(inboundPort);
        xInbound.setProtocol("vmess");
        xInbound.initSteamSettings();
        xInbound.initSniffing();
        List<XVmessClient> xVmessClientList = new ArrayList<>();
        serviceMap.forEach((serviceId, uuid) -> {
            XVmessClient xVmessClient = new XVmessClient(uuid, serviceId);
            xVmessClientList.add(xVmessClient);
        });
        xInbound.initVmessSetting(xVmessClientList);
        Map<String, Object> formData = BeanUtil.beanToMap(xInbound);
        HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.INBOUND_ADD)
                .cookie(this.cookie)
                .form(formData)
                .execute();
    }

    public void addVmessClient(XInbound inbound, String uuid, Long serviceId) {
        XInbound xInbound = new XInbound();
        xInbound.setId(inbound.getId());
        XVmessClient xVmessClient = new XVmessClient(uuid, serviceId);
        List<XVmessClient> xVmessClientList = Collections.singletonList(xVmessClient);
        xInbound.initVmessSetting(xVmessClientList);
        Map<String, Object> formData = BeanUtil.beanToMap(xInbound);
        HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.CLIENT_ADD)
                .cookie(this.cookie)
                .form(formData)
                .execute();
    }

    public void delVmessClient(int xInboundId, String uuid) {
        String path = String.format(XUIApi.CLIENT_DEL, xInboundId, uuid);
        HttpRequest.post("http://" + this.host + ":" + this.port + path)
                .cookie(this.cookie)
                .execute();
    }

    public void resetClientTraffic(int xInboundId) {
        String path = String.format(XUIApi.CLIENT_RESET_TRAFFIC, xInboundId);
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
//        server.setNodePort(20000);
//        server.setNodeRemark("ALADDIN");
        XUIClient xuiClient = XUIClient.init(server);
//        xuiClient.delInbound("5");
//        xuiClient.getInboundList();
//        xuiClient.getInboundOnline();
        NodeVmess nodeVmess = new NodeVmess();
        nodeVmess.setNodeId("1ba763c5-8e76-4e2d-974f-f6b6292cc5f4");
//        nodeVmess.setNodeId(UUID.fastUUID().toString());

//        List<NodeVmess> nodeVmessList = new ArrayList<>();
//        nodeVmessList.add(nodeVmess);
//        xuiClient.addVmessInbound(server, nodeVmessList);
//        xuiClient.addVmessClient(nodeVmess);
        xuiClient.getServerStatus();
    }
}
