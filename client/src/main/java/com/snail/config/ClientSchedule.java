package com.snail.config;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.utils.Executor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
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

    @Scheduled(cron = "0/10 * * * * ?")
    public void command() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(COMMAND_SERVER + "/command_record/get?ip=" + COMMAND_CLIENT);
        HttpPut httpPut = new HttpPut(COMMAND_SERVER + "/command_record/finish");
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
                        log.info("return command execute result request：{}", commandMap);
                        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(commandMap), "UTF-8");
                        httpPut.setEntity(stringEntity);
                        httpPut.setHeader("Content-Type", "application/json;charset=utf8");
                        CloseableHttpResponse putResponse = httpClient.execute(httpPut);
                        log.info("return command execute result response:{}", putResponse);
                    }
                }
            }
        } catch (IOException e) {
            log.error("execute command error：", e);
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
