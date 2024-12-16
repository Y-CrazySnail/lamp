package com.lamp.presentation.interceptor;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.lamp.domain.objvalue.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
public class LocalAuthInterceptor implements HandlerInterceptor {

    private final static ThreadLocal<Long> ID = new ThreadLocal<>();
    private static final ThreadLocal<Date> BEGIN_TIME = new NamedThreadLocal<>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().startsWith("/server/web/")) {
            Token token = new Token(request.getHeader("token"));
            if (!token.validate()) {
                log.error("token is invalid. token:{}", token);
                return false;
            }
            ID.set(token.parseJWTId());
            Date currentDate = new Date();
            BEGIN_TIME.set(currentDate);
            log.info("start time:{}, uri:{}, memberId:{}",
                    DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MS_PATTERN),
                    request.getRequestURI(),
                    ID.get()
            );
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (request.getRequestURI().startsWith("/server/web/")) {
            Date beginTime = BEGIN_TIME.get();
            Date endTime = new Date();
            log.info("end time:{}, time-consuming:{}, uri:{}, id:{}, max memory:{}MB, total memory:{}MB, free memory:{}MB",
                    DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MS_PATTERN),
                    DateUtil.between(beginTime, endTime, DateUnit.MS),
                    request.getRequestURI(),
                    ID.get(),
                    Runtime.getRuntime().maxMemory() / 1024 / 1024,
                    Runtime.getRuntime().totalMemory() / 1024 / 1024,
                    Runtime.getRuntime().freeMemory() / 1024 / 1024
            );
            ID.remove();
            BEGIN_TIME.remove();
        }
    }

    public static Long getMemberId() {
        return ID.get();
    }
}
