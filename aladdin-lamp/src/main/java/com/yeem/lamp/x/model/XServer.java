package com.yeem.lamp.x.model;

import lombok.Data;

import java.net.HttpCookie;

@Data
public class XServer {
    /**
     * 地址
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * 登录缓存
     */
    private HttpCookie cookie;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
