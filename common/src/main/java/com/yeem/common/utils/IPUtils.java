package com.yeem.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class IPUtils {
    /**
     * 获取IP
     *
     * @param request 请求
     * @return IP
     */
    public static String getIpAddress(HttpServletRequest request) {
        log.info(request.getRemoteHost());
        String xRealIP = request.getHeader("X-Real-IP");
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        // 多次反向代理后会有多个ip值，第一个ip才是真实ip
        if (!StringUtils.isEmpty(xForwardedFor) && !"unKnown".equalsIgnoreCase(xForwardedFor)) {
            int index = xForwardedFor.indexOf(",");
            if (index != -1) {
                return "0:0:0:0:0:0:0:1".equals(xForwardedFor.substring(0, index)) ? "127.0.0.1" : xForwardedFor.substring(0, index);
            } else {
                return "0:0:0:0:0:0:0:1".equals(xForwardedFor) ? "127.0.0.1" : xForwardedFor;
            }
        }
        xForwardedFor = xRealIP;
        if (!StringUtils.isEmpty(xForwardedFor) && !"unKnown".equalsIgnoreCase(xForwardedFor))
            return "0:0:0:0:0:0:0:1".equals(xForwardedFor) ? "127.0.0.1" : xForwardedFor;

        if (StringUtils.isEmpty(xForwardedFor) || "unknown".equalsIgnoreCase(xForwardedFor))
            xForwardedFor = request.getHeader("Proxy-Client-IP");

        if (StringUtils.isEmpty(xForwardedFor) || "unknown".equalsIgnoreCase(xForwardedFor))
            xForwardedFor = request.getHeader("WL-Proxy-Client-IP");

        if (StringUtils.isEmpty(xForwardedFor) || "unknown".equalsIgnoreCase(xForwardedFor))
            xForwardedFor = request.getHeader("HTTP_CLIENT_IP");

        if (StringUtils.isEmpty(xForwardedFor) || "unknown".equalsIgnoreCase(xForwardedFor))
            xForwardedFor = request.getHeader("HTTP_X_FORWARDED_FOR");

        if (StringUtils.isEmpty(xForwardedFor) || "unknown".equalsIgnoreCase(xForwardedFor))
            xForwardedFor = request.getRemoteAddr();

        return "0:0:0:0:0:0:0:1".equals(xForwardedFor) ? "127.0.0.1" : xForwardedFor;
    }
}
