package com.yeem.config;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.utils.ExecutorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(cron = "0/5 * * * * ?")
    public void responseCommand() {
        log.info("执行定时任务");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (Global.WAIT_BACK_RESULT.size() > 0) {
                Set<String> finishKey = new HashSet<>();
                Global.WAIT_BACK_RESULT.forEach((s, stringObjectMap) -> {
                    try {
                        HttpResponse httpResponse = HttpRequest.put(COMMAND_SERVER + "/command_record/finish")
                                .body(objectMapper.writeValueAsString(stringObjectMap))
                                .execute();
                        if (httpResponse.getStatus() == HttpStatus.HTTP_OK) {
                            finishKey.add(s);
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
                log.info("has finish uuid command: {}", finishKey);
                finishKey.forEach(Global.WAIT_BACK_RESULT::remove);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void requestCommand() {
        try {
            HttpResponse httpResponse = HttpRequest.get(COMMAND_SERVER + "/command_record/get?ip=" + COMMAND_CLIENT).execute();
            if (httpResponse.getStatus() == HttpStatus.HTTP_OK && !StringUtils.isEmpty(httpResponse.body())) {
                Map<String, Object> requestResult = objectMapper.readValue(
                        httpResponse.body(),
                        new TypeReference<Map<String, Object>>() {
                        }
                );
                log.info("响应体：{}", httpResponse.body());
                if (requestResult.containsKey("command") && !StringUtils.isEmpty(requestResult.get("command"))) {
                    String command = String.valueOf(requestResult.get("command"));
                    String uuid = IdUtil.simpleUUID();
                    String path = File.separator + "data" + File.separator + "snail" + File.separator + "sh" + File.separator + uuid;
                    log.info("sh file path: {}", path);
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String shellFile = path + File.separator + uuid + ".sh";
                    FileWriter fileWriter = new FileWriter(shellFile);
                    fileWriter.write(command);

                    String result = ExecutorUtils.execute("sh " + shellFile);
                    requestResult.put("flag", 1);
                    requestResult.put("result", result);
                    Global.WAIT_BACK_RESULT.put("uuid", requestResult);
                }
            }
        } catch (Exception e) {
            log.info("请求命令异常：", e);
        }
    }
}
