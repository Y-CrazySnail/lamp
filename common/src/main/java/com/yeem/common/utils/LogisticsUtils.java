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

    public static JsonNode query(String secretId, String secretKey,
                                 String mobile, String number) {
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
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("X-Source", source);
            headers.put("X-Date", datetime);
            headers.put("Authorization", auth);
            // body参数
            Map<String, String> bodyParams = new HashMap<String, String>();
            bodyParams.put("expressCode", "auto");
            bodyParams.put("mobile", mobile);
            bodyParams.put("number", number);
            // url参数拼接
            String url = "https://service-ootu039r-1305308687.sh.apigw.tencentcs.com/release/express/query/v1";
//            URL realUrl = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(5000);
//            conn.setRequestMethod(method);
//            // request headers
//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                conn.setRequestProperty(entry.getKey(), entry.getValue());
//            }
//            // request body
//            Map<String, Boolean> methods = new HashMap<>();
//            methods.put("POST", true);
//            methods.put("PUT", true);
//            methods.put("PATCH", true);
//            Boolean hasBody = methods.get(method);
//            if (hasBody != null) {
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setDoOutput(true);
//                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//                out.writeBytes(urlencode(bodyParams));
//                out.flush();
//                out.close();
//            }
//            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder("{\"data\":{\"status\":\"3\",\"expressCode\":\"zhongtong\",\"expressName\":\"中通快递\",\"logo\":\"https://img2.jumdata.com/express-logo/zhongtong.png\",\"number\":\"78714106471365\",\"logisticsList\":[{\"time\":\"2023-08-21 19:05:54\",\"context\":\"【北京市】 您的快递已签收，签收人在【原菜鸟】取件。如有疑问请联系：15843439519，网点电话：010-80909819，投诉电话：010-80909819。感谢使用中通快递，期待再次为您服务！\"},{\"time\":\"2023-08-18 16:44:03\",\"context\":\"【北京市】 快件已被 代收点 的【原菜鸟】代收，【取件地址：东三旗村委会394号】，请及时取件。如有疑问请联系：15843439519，投诉电话：010-80909819\"},{\"time\":\"2023-08-18 14:18:58\",\"context\":\"【北京市】北京北七家（010-80909819）业务员【李洪生18910283546,18911960306】正在为您派件（95720为中通快递员外呼专属号码，请放心接听，如有问题可联系投诉电话：010-80909819）\"},{\"time\":\"2023-08-18 14:18:57\",\"context\":\"【北京市】 快件已到达 北京北七家\"},{\"time\":\"2023-08-18 14:17:38\",\"context\":\"【保定市】 快件已发往 北京北七家\"},{\"time\":\"2023-08-18 03:27:22\",\"context\":\"【保定市】 快件已到达 京南转运中心\"},{\"time\":\"2023-08-17 03:06:04\",\"context\":\"【武汉市】 快件已发往 京南转运中心\"},{\"time\":\"2023-08-17 03:04:09\",\"context\":\"【武汉市】 快件已到达 武汉中转部\"},{\"time\":\"2023-08-16 22:41:48\",\"context\":\"【荆州市】 快件已发往 北京北七家\"},{\"time\":\"2023-08-16 22:41:31\",\"context\":\"【荆州市】 快件已到达 荆州中转部\"},{\"time\":\"2023-08-16 15:24:53\",\"context\":\"【宜昌市】 快件已发往 荆州中转部\"},{\"time\":\"2023-08-16 15:24:29\",\"context\":\"【宜昌市】 【宜昌当阳】（0717-6996253） 的 当阳zto（15171874755） 已揽收\"}]},\"msg\":\"成功\",\"success\":true,\"code\":200,\"taskNo\":\"030039835227820237115994\",\"charge\":true}");
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
            log.info("result:{}", result);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result.toString());
            if (200 == jsonNode.get("code").asInt()) {
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
