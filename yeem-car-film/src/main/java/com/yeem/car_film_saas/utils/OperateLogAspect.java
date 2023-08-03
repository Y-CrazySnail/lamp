package com.yeem.car_film_saas.utils;

import com.alibaba.fastjson.JSON;
import io.micrometer.core.instrument.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private SysLogMapper sysLogMapper;

    private static ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    // 设置操作日志切入点 记录操作日志 在注解的位置切入代码
    @Pointcut("@annotation(com.yeem.car_film_saas.utils.OperLog)")
    public void operatePointCut() {
    }


    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.yeem.car_film_saas.controller..*.*(..))")
    public void operateExceptionPoinCut() {
    }


    /**
     * 前置通知
     * 获取开始的毫秒值
     */
    @Before("operatePointCut()||operateExceptionPoinCut()")
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
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        saveLogInfo(joinPoint, keys, null);
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operateExceptionPoinCut()", throwing = "e")
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
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
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
            sysLog.setOperateMethod(methodName); // 请求方法
            // 请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);

            // 获取操作

            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                String operModul = opLog.operModul();
                String operType = opLog.operType();
                String operDesc = opLog.operDesc();
                sysLog.setOperateModule(operModul);
                sysLog.setOperateType(operType);
                sysLog.setOperateDesc(operDesc);
            }

            if (e != null) {
                // 异常名称
                sysLog.setExceptionName(e.getClass().getName());
                // 异常信息
                sysLog.setExceptionMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            }

            //获取线程id
            int threadId = (int) Thread.currentThread().getId();
            sysLog.setThread(threadId);
            sysLog.setRequestParam(params);
            sysLog.setResponseParam(JSON.toJSONString(keys));
            sysLog.setUserId("1");    // 这边自己公司的工具类
            sysLog.setUserName("qimou");// 你们可把用户信息放在Request中获取
            sysLog.setIp(getIpAddressa(request));
            sysLog.setUri(request.getRequestURI());
            sysLog.setCreateTime(new Date());
            sysLog.setRequestState(e == null ? 1 : 2);
            sysLog.setSessionTime((int) ((System.currentTimeMillis() - startTime.get()) / 1000));
            //插入数据到数据库
            sysLogMapper.insert(sysLog);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
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
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }


    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIpAddressa(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");

        //多次反向代理后会有多个ip值，第一个ip才是真实ip
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            int index = XFor.indexOf(",");
            if (index != -1) {
                return "0:0:0:0:0:0:0:1".equals(XFor.substring(0, index)) ? "127.0.0.1" : XFor.substring(0, index);
            } else {
                return "0:0:0:0:0:0:0:1".equals(XFor) ? "127.0.0.1" : XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor))
            return "0:0:0:0:0:0:0:1".equals(XFor) ? "127.0.0.1" : XFor;

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("Proxy-Client-IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("WL-Proxy-Client-IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("HTTP_CLIENT_IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getRemoteAddr();

        return "0:0:0:0:0:0:0:1".equals(XFor) ? "127.0.0.1" : XFor;
    }


}
