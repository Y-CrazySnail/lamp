package com.yeem.lamp.infrastructure.x;

public class XUIApi {
    public static final String LOGIN = "/login";
    public static final String INBOUND_LIST = "/panel/inbound/list";
    public static final String INBOUND_ONLINE = "/panel/inbound/onlines";
    public static final String INBOUND_DEL = "/panel/inbound/del/%s";
    public static final String INBOUND_ADD = "/panel/inbound/add";
    public static final String CLIENT_ADD = "/panel/inbound/addClient";
    public static final String CLIENT_DEL = "/panel/inbound/%s/delClient/%s";
    public static final String CLIENT_RESET_TRAFFIC = "/panel/inbound/resetAllClientTraffics/%s";
    public static final String SERVER_STATUS = "/server/status";
}
