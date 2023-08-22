package com.yeem.zero.controller.wechat;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpStatus;
import com.qcloud.cos.model.UploadResult;
import com.yeem.common.conreoller.BaseController;
import com.yeem.common.utils.TencentFileUtils;
import com.yeem.zero.entity.ZeroTencentCos;
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
@RequestMapping("/wechat-zero-tencent-cos")
public class ZeroTencentCosController {

    @Autowired
    private Environment environment;

    @PostMapping("upload")
    public ResponseEntity<Object> upload(@RequestPart("file") MultipartFile file) {
        String key = UUID.fastUUID() + "." + FileUtil.getSuffix(file.getOriginalFilename());
        UploadResult uploadResult;
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
        } catch (IOException e) {
            log.error("upload file to tencent cos error:", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("upload file to tencent cos error");
        }
        return ResponseEntity.ok(key);
    }
}
