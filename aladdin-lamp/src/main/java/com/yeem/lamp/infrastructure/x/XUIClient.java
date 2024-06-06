package com.yeem.lamp.infrastructure.x;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import com.yeem.lamp.infrastructure.x.model.XLogin;
import com.yeem.lamp.infrastructure.x.model.XResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public static XUIClient init(String host, int port, String username, String password) {
        if (XUI_CLIENT_MAP.containsKey(host)) {
            XUIClient xuiClient = XUI_CLIENT_MAP.get(host);
            if (!xuiClient.expired()) {
                return xuiClient;
            } else {
                XUI_CLIENT_MAP.remove(host);
            }
        }
        XUIClient xuiClient = login(host, port, username, password);
        XUI_CLIENT_MAP.put(host, xuiClient);
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

    public Object getInboundList() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.INBOUND_LIST)
                .cookie(this.cookie)
                .execute();
        String body = response.body();
        try {
            XResponse<List<XInbound>> xResponse = objectMapper.readValue(body, new TypeReference<XResponse<List<XInbound>>>() {
            });
            List<XInbound> xInboundList = xResponse.getObj();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean delInbound() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response = HttpRequest.post("http://" + this.host + ":" + this.port + XUIApi.INBOUND_DEL + "id")// todo 替换
                .cookie(this.cookie)
                .execute();
        String body = response.body();
        try {
            XResponse<Object> xResponse = objectMapper.readValue(body, new TypeReference<Object>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
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

    public static void main(String[] args) {
        XUIClient xuiClient = XUIClient.init("107.6.243.224", 9999, "aladdin", "aladdin");
        xuiClient.getInboundList();
        xuiClient.getInboundOnline();
        System.out.println("123");
    }
}
