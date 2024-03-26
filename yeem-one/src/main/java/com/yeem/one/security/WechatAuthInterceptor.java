package com.yeem.one.security;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.yeem.one.util.WechatJWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
public class WechatAuthInterceptor implements HandlerInterceptor {

    private final static ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();
    private final static ThreadLocal<Long> ID = new ThreadLocal<>();
    private final static ThreadLocal<String> OPEN_ID = new ThreadLocal<>();
    private static final ThreadLocal<Date> BEGIN_TIME = new NamedThreadLocal<>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().startsWith("/one/wechat/")) {
            String token = request.getHeader("token");
            if (!WechatJWTUtils.validate(token)) {
                log.error("token is invalid. token:{}", token);
                return false;
            }
            TENANT_ID.set(WechatJWTUtils.parseJWTTenantId(token));
            ID.set(WechatJWTUtils.parseJWTId(token));
            OPEN_ID.set(WechatJWTUtils.parseJWTOpenId(token));
            Date currentDate = new Date();
            BEGIN_TIME.set(currentDate);
            log.info("start time:{}, uri:{}, tenantId:{}, id:{}, openId: {}",
                    DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MS_PATTERN),
                    request.getRequestURI(),
                    TENANT_ID.get(),
                    ID.get(),
                    OPEN_ID.get()
            );
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (request.getRequestURI().startsWith("/server/wechat/")) {
            Date beginTime = BEGIN_TIME.get();
            Date endTime = new Date();
            log.info("end time:{}, time-consuming:{}, uri:{}, application:{}, id:{}, openId: {}, " +
                            "max memory:{}MB, total memory:{}MB, free memory:{}MB",
                    DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MS_PATTERN),
                    DateUtil.between(beginTime, endTime, DateUnit.MS),
                    request.getRequestURI(),
                    null != TENANT_ID.get() ? "" : TENANT_ID.get(),
                    null == ID.get() ? "" : ID.get(),
                    StrUtil.isEmpty(OPEN_ID.get()) ? "" : OPEN_ID.get(),
                    Runtime.getRuntime().maxMemory() / 1024 / 1024,
                    Runtime.getRuntime().totalMemory() / 1024 / 1024,
                    Runtime.getRuntime().freeMemory() / 1024 / 1024
            );
            try {
                TENANT_ID.remove();
                ID.remove();
                OPEN_ID.remove();
                BEGIN_TIME.remove();
            } catch (Exception ignored) {
            }
        }
    }

    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    public static Long getUserId() {
        return ID.get();
    }

    public static String getOpenId() {
        return OPEN_ID.get();
    }
}
