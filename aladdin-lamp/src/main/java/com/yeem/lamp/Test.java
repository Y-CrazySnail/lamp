package com.yeem.lamp;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.lamp.xui.XUIRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {
//    public static void main(String[] args) {
//        try {
//            XUIRequest.request().ip("107.6.243.224").port("9999").login("aladdin", "aladdin").sync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, String> loginBody = new HashMap<>();
//        loginBody.put("username", "aladdin");
//        loginBody.put("password", "aladdin");
//        try {
//            HttpResponse logigResponse = HttpRequest.post("http://107.6.243.224:9999/login")
//                    .body(objectMapper.writeValueAsString(loginBody))
//                    .execute();
//            System.out.println(logigResponse.getCookie("session"));
//            HttpResponse response = HttpRequest.post("http://107.6.243.224:9999/xui/inbound/addClient")
//                    .cookie(logigResponse.getCookie("session"))
//                    .form("id", "1")
//                    .form("settings",  "{\"clients\": [{\n" +
//                            "  \"id\": \"4eeefda9-7414-43b2-8ad1-e689a2cda229\",\n" +
//                            "  \"email\": \"r4g1gmwjf\",\n" +
//                            "  \"totalGB\": 0,\n" +
//                            "  \"expiryTime\": 0,\n" +
//                            "  \"enable\": true,\n" +
//                            "  \"tgId\": \"\",\n" +
//                            "  \"subId\": \"tl0tan5k4wyg1ldy\",\n" +
//                            "  \"reset\": 0\n" +
//                            "}]}")
//                    .execute();
//            System.out.println(response);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
}
