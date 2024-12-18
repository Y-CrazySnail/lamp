package com.lamp.xui;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.domain.entity.Server;
import com.lamp.domain.objvalue.NodeVmess;
import com.lamp.xui.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
public class XClient {

    public static final String LOGIN = "/login";
    public static final String INBOUND_LIST = "/panel/inbound/list";
    public static final String INBOUND_ONLINE = "/panel/inbound/onlines";
    public static final String INBOUND_DEL = "/panel/inbound/del/%s";
    public static final String INBOUND_ADD = "/panel/inbound/add";
    public static final String CLIENT_ADD = "/panel/inbound/addClient";
    public static final String CLIENT_DEL = "/panel/inbound/%s/delClient/%s";
    public static final String CLIENT_RESET_TRAFFIC = "/panel/inbound/resetAllClientTraffics/%s";
    public static final String SERVER_STATUS = "/server/status";
    
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

    public static Map<String, XClient> XUI_CLIENT_MAP = new ConcurrentHashMap<>();

    public static XClient init(String apiIp, Integer apiPort, String apiUsername, String apiPassword) {
        String key = apiIp + apiPort + apiUsername + apiPassword;
        if (XUI_CLIENT_MAP.containsKey(key)) {
            XClient xClient = XUI_CLIENT_MAP.get(key);
            if (!xClient.expired()) {
                return xClient;
            } else {
                XUI_CLIENT_MAP.remove(key);
            }
        }
        XClient xClient = login(apiIp, apiPort, apiUsername, apiPassword);
        XUI_CLIENT_MAP.put(key, xClient);
        return xClient;
    }

    private static XClient login(String host, int port, String username, String password) {
        ObjectMapper objectMapper = new ObjectMapper();
        XLogin xLogin = new XLogin();
        xLogin.setUsername(username);
        xLogin.setPassword(password);
        HttpResponse response;
        try {
            response = HttpRequest.post("http://" + host + ":" + port + LOGIN)
                    .body(objectMapper.writeValueAsString(xLogin))
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            XResponse<Object> xResponse = objectMapper.readValue(response.body(), new TypeReference<XResponse<Object>>() {
            });
            if (null != xResponse && xResponse.isSuccess()) {
                XClient xClient = new XClient();
                xClient.setHost(host);
                xClient.setPort(port);
                xClient.setUsername(username);
                xClient.setPassword(password);
                xClient.setRemark("");
                xClient.setCookie(response.getCookie("session"));
                xClient.setExpireDate(DateUtil.offsetDay(new Date(), 30));
                return xClient;
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<XInbound> getInbound() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + INBOUND_LIST)
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

    public void delInbound(XInbound xInbound) {
        String path = String.format(INBOUND_DEL, xInbound.getId());
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + path)
                .cookie(this.cookie)
                .execute();
    }

    public void addVmessInbound(Long serverId, int inboundPort, Map<Long, String> serviceMap) {
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
        HttpRequest.post("http://" + this.host + ":" + this.port + INBOUND_ADD)
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
        HttpRequest.post("http://" + this.host + ":" + this.port + CLIENT_ADD)
                .cookie(this.cookie)
                .form(formData)
                .execute();
    }

    public void delVmessClient(int xInboundId, String uuid) {
        String path = String.format(CLIENT_DEL, xInboundId, uuid);
        HttpRequest.post("http://" + this.host + ":" + this.port + path)
                .cookie(this.cookie)
                .execute();
    }

    public void resetClientTraffic(int xInboundId) {
        String path = String.format(CLIENT_RESET_TRAFFIC, xInboundId);
        HttpRequest.post("http://" + this.host + ":" + this.port + path)
                .cookie(this.cookie)
                .execute();
    }

    public Object getInboundOnline() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + INBOUND_ONLINE)
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
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + SERVER_STATUS)
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
        XClient xClient = XClient.init("", 1, "", "");
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
        xClient.getServerStatus();
    }
}
