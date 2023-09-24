package com.yeem.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.utils.IPUtils;
import com.yeem.common.utils.OauthUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private SysLogMapper sysLogMapper;

    private static ThreadLocal<Long> startTime = new ThreadLocal<>();

    // 设置操作日志切入点 记录操作日志 在注解的位置切入代码
    @Pointcut("@annotation(com.yeem.log.OperateLog)")
    public void operatePointCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.yeem.*.controller.*.*(..))")
    public void operateExceptionPointCut() {
    }

    /**
     * 前置通知
     * 获取开始的毫秒值
     */
    @Before("operatePointCut()||operateExceptionPointCut()")
    public void doBefore() {
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 返回通知
     * 拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operatePointCut()", returning = "keys")
    public void saveOperateLog(JoinPoint joinPoint, Object keys) {
        saveLogInfo(joinPoint, keys, null);
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operateExceptionPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        saveLogInfo(joinPoint, null, e);
    }

    /**
     * 保存日志信息
     *
     * @param joinPoint 切点
     * @param keys      返回值
     * @param e         异常类
     */
    public void saveLogInfo(JoinPoint joinPoint, Object keys, Throwable e) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletRequest request = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!StringUtils.isEmpty(requestAttributes)) {
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }
        SysLog sysLog = new SysLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            sysLog.setOperateMethod(methodName);
            String params = "";
            if (!StringUtils.isEmpty(request)) {
                // 请求的参数
                Map<String, String> requestMap = converMap(request.getParameterMap());
                // 将参数所在的数组转换成json
                params = objectMapper.writeValueAsString(requestMap);
            }
            // 获取操作
            OperateLog opLog = method.getAnnotation(OperateLog.class);
            if (opLog != null) {
                sysLog.setOperateModule(opLog.operateModule());
                sysLog.setOperateType(opLog.operateType());
                sysLog.setOperateDesc(opLog.operateDesc());
            }
            if (e != null) {
                // 异常名称
                sysLog.setExceptionName(e.getClass().getName());
                // 异常信息
                sysLog.setExceptionMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            }
            // 获取线程id
            int threadId = (int) Thread.currentThread().getId();
            sysLog.setThread(threadId);
            sysLog.setRequestParam(params);
            sysLog.setResponseParam(objectMapper.writeValueAsString(keys));
            sysLog.setUserName(OauthUtils.getUsername());
            if (!StringUtils.isEmpty(request)) {
                sysLog.setIp(IPUtils.getIpAddress(request));
                sysLog.setUri(request.getRequestURI());
            }
            sysLog.setCreateTime(new Date());
            sysLog.setRequestState(e == null ? 1 : 2);
            sysLog.setSessionTime((int) ((System.currentTimeMillis() - startTime.get()) / 1000));
            sysLogMapper.insert(sysLog);
        } catch (Exception exception) {
            log.error("save operate log error:", exception);
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement element : elements) {
            stringBuilder.append(element).append("\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n\t" + stringBuilder;
    }
}