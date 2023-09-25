package com.yeem.zero.controller.web;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpStatus;
import com.yeem.common.utils.TencentFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/manager-zero-tencent-cos")
public class ZeroMTencentCosController {

    @Autowired
    private Environment environment;

    @PostMapping("upload")
    public ResponseEntity<Object> upload(@RequestPart("file") MultipartFile file) {
        String key = UUID.fastUUID() + "/" + file.getOriginalFilename();
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
