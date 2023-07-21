package com.yeem.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Global {
    /**
     * 待回传结果
     */
    public static final Map<String, Map<String, Object>> WAIT_BACK_RESULT = new ConcurrentHashMap<>();
}
