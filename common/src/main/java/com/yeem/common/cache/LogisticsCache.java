package com.yeem.common.cache;

import com.fasterxml.jackson.databind.JsonNode;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

/**
 * 物流缓存
 */
public class LogisticsCache {
    public static final ExpiringMap<String, JsonNode> LOGISTICS_CACHE = ExpiringMap
            .builder()
            .maxSize(500)
            .expiration(1, TimeUnit.HOURS)
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .build();
}
