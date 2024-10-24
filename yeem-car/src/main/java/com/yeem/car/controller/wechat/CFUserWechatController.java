package com.yeem.car.controller.wechat;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpStatus;
import com.yeem.car.entity.CFUser;
import com.yeem.car.service.wechat.WechatCFUserService;
import com.yeem.common.utils.TencentFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/wechat/user")
public class CFUserWechatController {

    @Value("${tencent.cos.bucket-name}")
    private String TENCENT_COS_BUCKET_NAME;
    @Value("${tencent.cos.secret-id}")
    private String TENCENT_COS_SECRET_ID;
    @Value("${tencent.cos.secret-key}")
    private String TENCENT_COS_SECRET_KEY;
    @Value("${tencent.cos.region}")
    private String TENCENT_COS_REGION;


    @Autowired
    private WechatCFUserService userService;

    /**
     * 小程序登录
     *
     * @return 登录信息
     * @apiNote 小程序登录
     */
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody CFUser user) {
        try {
            return ResponseEntity.ok(userService.login(user));
        } catch (Exception e) {
            log.error("小程序登录异常：", e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("upload")
    public ResponseEntity<Object> upload(@RequestPart("file") MultipartFile file) {
        String key = UUID.fastUUID() + "/" + file.getOriginalFilename();
        try {
            TencentFileUtils.upload(TENCENT_COS_BUCKET_NAME, TENCENT_COS_SECRET_ID, TENCENT_COS_SECRET_KEY, TENCENT_COS_REGION, key, file.getInputStream());
            log.info("upload file to tencent cos, key:{}", key);
            String url = TencentFileUtils.getUrl(TENCENT_COS_BUCKET_NAME, TENCENT_COS_REGION, key).toString();
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            log.error("upload file to tencent cos error:", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("upload file to tencent cos error");
        }
    }

}
