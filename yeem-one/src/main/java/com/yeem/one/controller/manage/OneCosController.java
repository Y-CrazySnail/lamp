package com.yeem.one.controller.manage;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpStatus;
import com.yeem.common.utils.TencentFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/manage/cos")
public class OneCosController {

    private static final String APPLICATION = "yeem_one";

    @Autowired
    private Environment environment;

    @PostMapping("upload")
    public ResponseEntity<Object> upload(@RequestPart("file") MultipartFile file,
                                         @RequestParam(value = "path", required = false) String path) {
        String key = UUID.fastUUID() + "/" + file.getOriginalFilename();
        if (!StringUtils.isEmpty(path)) {
            key = String.format("%s/%s", path, key);
        }
        key = String.format("%s%s", APPLICATION, key);
        try {
            TencentFileUtils.upload(
                    environment.getProperty("tencent.cos.bucket-name"),
                    environment.getProperty("tencent.cos.secret-id"),
                    environment.getProperty("tencent.cos.secret-key"),
                    environment.getProperty("tencent.cos.region"),
                    key,
                    file.getInputStream()
            );
            log.info("upload file to tencent cos, key:{}", key);
            String url = TencentFileUtils.getUrl(
                            environment.getProperty("tencent.cos.bucket-name"),
                            environment.getProperty("tencent.cos.region"),
                            key)
                    .toString();
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            log.error("upload file to tencent cos error:", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("upload file to tencent cos error");
        }
    }
}
