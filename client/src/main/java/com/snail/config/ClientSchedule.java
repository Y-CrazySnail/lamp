package com.snail.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.utils.Executor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
public class ClientSchedule {

    /**
     * 命令服务地址
     */
    @Value("${command.server}")
    private String COMMAND_SERVER;

    /**
     * 命令客户端地址
     */
    @Value("${command.client}")
    private String COMMAND_CLIENT;

    @Scheduled(cron = "0 20 0 * * ?")
    public void schedule() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://42.192.164.50:9999/proxy/node/getConfiguration");
        HttpPost httpPost = new HttpPost("http://42.192.164.50:9999/proxy/traffic/save");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                String configuration = EntityUtils.toString(httpEntity);
                BufferedWriter out = new BufferedWriter(new FileWriter("/usr/local/etc/xray/config.json", false));
                out.write(configuration);
                out.close();
                String status = Executor.execute("xray api statsquery --server=127.0.0.1:10085  'pattern: \"\" reset: true'");
                BufferedWriter out1 = new BufferedWriter(new FileWriter("/usr/local/etc/xray/1.json", false));
                out1.write(status);
                out1.close();
                StringEntity stringEntity = new StringEntity(status, "UTF-8");
                httpPost.setEntity(stringEntity);
                httpPost.setHeader("Content-Type", "application/json;charset=utf8");
                httpClient.execute(httpPost);
                Executor.execute("systemctl restart xray");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void command() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(COMMAND_SERVER + "/command_record/get?ip=" + COMMAND_CLIENT);
        HttpPut httpPut = new HttpPut(COMMAND_SERVER + "/command_record/baseUpdate");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                String command = EntityUtils.toString(httpEntity);
                if (!StringUtils.isEmpty(command)) {
                    log.info("execute log：{}", command);
                    String uuid = IdUtil.simpleUUID();
                    String path = File.separator + "data" + File.separator + "snail" + File.separator + "sh" + File.separator + uuid;
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    log.info("sh file path: {}", path);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> commandMap = objectMapper.readValue(command, Map.class);
                    if (!StringUtils.isEmpty(commandMap.get("command"))) {
                        String shellFile = path + File.separator + uuid + ".sh";
                        cn.hutool.core.io.file.FileWriter fileWriter = new cn.hutool.core.io.file.FileWriter(shellFile);
                        fileWriter.write(String.valueOf(commandMap.get("command")));
                        String result = Executor.execute("sh " + shellFile);
                        commandMap.put("flag", 1);
                        commandMap.put("result", result);
                        commandMap.put("executeEndTime", LocalDateTime.now());
                        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(commandMap), "UTF-8");
                        httpPut.setEntity(stringEntity);
                        httpPut.setHeader("Content-Type", "application/json;charset=utf8");
                        httpClient.execute(httpPut);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
