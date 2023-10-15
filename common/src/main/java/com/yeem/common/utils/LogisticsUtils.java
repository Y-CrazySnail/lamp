package com.yeem.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.cache.LogisticsCache;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

@Slf4j
public class LogisticsUtils {
    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes(StandardCharsets.UTF_8));
        String sig = new BASE64Encoder().encode(hash);

        return "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
    }

    public static JsonNode query(String secretId, String secretKey,
                                 String mobile, String number) {
        if (LogisticsCache.LOGISTICS_CACHE.containsKey(number)) {
            return LogisticsCache.LOGISTICS_CACHE.get(number);
        } else {
            BufferedReader in = null;
            try {
                String source = "market";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                String datetime = simpleDateFormat.format(calendar.getTime());
                // 签名
                String auth = calcAuthorization(source, secretId, secretKey, datetime);
                // 请求方法
                String method = "POST";
                // 请求头
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Source", source);
                headers.put("X-Date", datetime);
                headers.put("Authorization", auth);
                // body参数
                Map<String, String> bodyParams = new HashMap<>();
                bodyParams.put("expressCode", "auto");
                bodyParams.put("mobile", mobile);
                bodyParams.put("number", number);
                // url参数拼接
                String url = "https://service-ootu039r-1305308687.sh.apigw.tencentcs.com/release/express/query/v1";
                URL realUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod(method);
                // request headers
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
                // request body
                Map<String, Boolean> methods = new HashMap<>();
                methods.put("POST", true);
                methods.put("PUT", true);
                methods.put("PATCH", true);
                Boolean hasBody = methods.get(method);
                ObjectMapper objectMapper = new ObjectMapper();
                if (hasBody != null) {
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(bodyParams));
                    out.flush();
                    out.close();
                }
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                log.info("result:{}", result);
                JsonNode jsonNode = objectMapper.readTree(result.toString());
                if (200 == jsonNode.get("code").asInt()) {
                    LogisticsCache.LOGISTICS_CACHE.put(number, jsonNode.get("data"));
                    return jsonNode.get("data");
                } else {
                    return null;
                }
            } catch (Exception e) {
                log.error("error:", e);
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        }
    }

    public static String urlencode(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }

}
