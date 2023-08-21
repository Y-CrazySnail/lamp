package com.yeem.zero.controller.wechat;

import com.yeem.common.conreoller.BaseController;
import com.yeem.common.utils.OauthUtils;
import com.yeem.common.utils.TencentFileUtils;
import com.yeem.zero.entity.ZeroAddress;
import com.yeem.zero.service.IZeroAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/wechat-zero-address")
public class ZeroAddressController extends BaseController<ZeroAddress> {

    @Autowired
    private IZeroAddressService zeroAddressService;

    @Autowired
    private Environment environment;

    @GetMapping("listByUsername")
    public ResponseEntity<Object> listByUsername() {
        try {
            String username = OauthUtils.getUsername();
            if (StringUtils.isEmpty(username)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no login");
            }
            return ResponseEntity.ok(zeroAddressService.listByUsername(username));
        } catch (Exception e) {
            log.error("list address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("list address error");
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody ZeroAddress zeroAddress) {
        try {
            zeroAddressService.save(zeroAddress);
            return ResponseEntity.ok("save address success");
        } catch (Exception e) {
            log.error("save address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("save address error");
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody ZeroAddress zeroAddress) {
        try {
            zeroAddressService.update(zeroAddress);
            return ResponseEntity.ok("update address success");
        } catch (Exception e) {
            log.error("update address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("update address error");
        }
    }

    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody ZeroAddress zeroAddress) {
        try {
            if (StringUtils.isEmpty(zeroAddress.getId())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("address id is null");
            }
            zeroAddressService.removeById(zeroAddress);
            return ResponseEntity.ok("remove address success");
        } catch (Exception e) {
            log.error("remove address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("remove address error");
        }
    }

    @GetMapping("test")
    public ResponseEntity<Object> test() {
        String localFilePath = "/Users/snail/Desktop/logo.png";
        File localFile = new File(localFilePath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(localFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        TencentFileUtils.upload(
                environment.getProperty("tencent.cos.bucket-name"),
                environment.getProperty("tencent.cos.secret-id"),
                environment.getProperty("tencent.cos.secret-key"),
                environment.getProperty("tencent.cos.region"),
                "logo1.png",
                inputStream
        );
        TencentFileUtils.generatePreSignedUrl(
                environment.getProperty("tencent.cos.bucket-name"),
                environment.getProperty("tencent.cos.secret-id"),
                environment.getProperty("tencent.cos.secret-key"),
                environment.getProperty("tencent.cos.region"),
                "logo1.png"
        );
        return null;
    }
}
