package com.lamp.xui;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.xui.entity.XuiInbound;
import com.lamp.xui.entity.XuiResponse;
import com.lamp.xui.entity.XuiSettings;
import com.lamp.xui.entity.XuiVmessSettings;
import com.lamp.xui.model.*;
import com.lamp.xui.parser.SettingParser;
import com.lamp.xui.parser.VmessSettingParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Data
@Slf4j
public class XServer {

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

    public static Map<String, XServer> XUI_CLIENT_MAP = new ConcurrentHashMap<>();

    public static XServer init(String apiIp, Integer apiPort, String apiUsername, String apiPassword) {
        String key = apiIp + apiPort + apiUsername + apiPassword;
        if (XUI_CLIENT_MAP.containsKey(key)) {
            XServer xServer = XUI_CLIENT_MAP.get(key);
            if (!xServer.expired()) {
                return xServer;
            } else {
                XUI_CLIENT_MAP.remove(key);
            }
        }
        XServer xServer = login(apiIp, apiPort, apiUsername, apiPassword);
        XUI_CLIENT_MAP.put(key, xServer);
        return xServer;
    }

    private static XServer login(String host, int port, String username, String password) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> xLogin = new HashMap<>();
        xLogin.put("username", username);
        xLogin.put("password", password);
        HttpResponse response;
        try {
            response = HttpRequest.post("http://" + host + ":" + port + LOGIN)
                    .body(objectMapper.writeValueAsString(xLogin))
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            XuiResponse<Object> xuiResponse = objectMapper.readValue(response.body(), new TypeReference<XuiResponse<Object>>() {
            });
            if (null != xuiResponse && xuiResponse.isSuccess()) {
                XServer xServer = new XServer();
                xServer.setHost(host);
                xServer.setPort(port);
                xServer.setUsername(username);
                xServer.setPassword(password);
                xServer.setRemark("");
                xServer.setCookie(response.getCookie("session"));
                xServer.setExpireDate(DateUtil.offsetDay(new Date(), 30));
                return xServer;
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object serverStatus() {
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + SERVER_STATUS)
                .cookie(this.cookie)
                .execute();
        return response.body();
    }

    public List<XuiInbound> inboundList(Long id) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + INBOUND_LIST)
                .cookie(this.cookie)
                .execute();
        String body = response.body();
        try {
            XuiResponse<List<XuiInbound>> xuiResponse = objectMapper.readValue(body, new TypeReference<XuiResponse<List<XuiInbound>>>() {
            });
            if (xuiResponse.isSuccess()) {
                List<XuiInbound> xuiInboundList = xuiResponse.getObj();
                if (Objects.isNull(xuiInboundList) || xuiInboundList.isEmpty()) {
                    return new ArrayList<>();
                }
                if (Objects.nonNull(id)) {
                    xuiInboundList = xuiInboundList.stream().filter(xuiInbound -> xuiInbound.getId() == id).collect(Collectors.toList());
                }
                for (XuiInbound xuiInbound : xuiInboundList) {
                    if ("vmess".equals(xuiInbound.getProtocol())) {
                        SettingParser parser = new VmessSettingParser();
                        parser.parse(xuiInbound);
                    }
                }
                return xuiInboundList;
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void inboundDelete(Long xInboundId) {
        String path = String.format(INBOUND_DEL, xInboundId);
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + path)
                .cookie(this.cookie)
                .execute();
    }

    public XuiInbound inboundAdd(XuiInbound xuiInbound) {
        Map<String, Object> formData = BeanUtil.beanToMap(xuiInbound);
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + INBOUND_ADD)
                .cookie(this.cookie)
                .form(formData)
                .execute();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            XuiResponse<XuiInbound> xuiResponse = objectMapper.readValue(response.body(), new TypeReference<XuiResponse<XuiInbound>>() {
            });
            if (xuiResponse.isSuccess()) {
                xuiInbound = xuiResponse.getObj();
                if (Objects.equals("vmess", xuiInbound.getProtocol())) {
                    SettingParser parser = new VmessSettingParser();
                    parser.parse(xuiInbound);
                }
                return xuiInbound;
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void addClient(XuiInbound xuiInbound) {
        Map<String, Object> formData = BeanUtil.beanToMap(xuiInbound);
        HttpRequest.post("http://" + this.host + ":" + this.port + CLIENT_ADD)
                .cookie(this.cookie)
                .form(formData)
                .execute();
    }

    public void deleteClient(long xInboundId, String uuid) {
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

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        XServer xServer = XServer.init("136.175.177.16", 9999, "aladdin-tw", "aladdin-tw");
        String status = objectMapper.writeValueAsString(xServer.serverStatus());
        List<XuiInbound> xuiInboundList = xServer.inboundList(null);
        for (XuiInbound xuiInbound : xuiInboundList) {
            if (!"vmess".equals(xuiInbound.getProtocol())) {
                xServer.inboundDelete(xuiInbound.getId());
            }
        }
        xuiInboundList = xServer.inboundList(null);
        for (XuiInbound xuiInbound : xuiInboundList) {
            if (xuiInbound.getClientStats().size() == 2) {
                XuiVmessSettings xuiVmessSettings = (XuiVmessSettings) xuiInbound.getSettingsObject();
                xServer.deleteClient(xuiInbound.getId(), xuiVmessSettings.getClients().get(0).getId());
            }
        }
        xuiInboundList = xServer.inboundList(null);
        System.out.println(objectMapper.writeValueAsString(xuiInboundList));
    }
}
