package com.yeem.zero.controller.wechat;

import com.yeem.zero.log.OperateLog;
import com.yeem.zero.entity.ZeroAddress;
import com.yeem.zero.security.WechatAuthInterceptor;
import com.yeem.zero.service.IZeroAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序-地址信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat/zero-address")
public class ZeroAddressController {

    @Autowired
    private IZeroAddressService zeroAddressService;

    @Autowired
    private Environment environment;

    /**
     * 根据用户名查询地址列表
     *
     * @return 地址信息列表
     * @apiNote 根据用户名查询地址列表
     */
    @GetMapping("list")
    public ResponseEntity<List<ZeroAddress>> list() {
        try {
            Long userId = WechatAuthInterceptor.getUserId();
            return ResponseEntity.ok(zeroAddressService.listByUserId(userId));
        } catch (Exception e) {
            log.error("list address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 保存地址信息
     *
     * @param zeroAddress 地址信息
     * @return 保存状态
     * @apiNote 保存地址信息
     */
    @OperateLog(operateModule = "地址模块", operateType = "保存", operateDesc = "保存地址")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody ZeroAddress zeroAddress) {
        try {
            Long userId = WechatAuthInterceptor.getUserId();
            zeroAddress.setUserId(userId);
            zeroAddressService.save(zeroAddress);
            return ResponseEntity.ok("save address success");
        } catch (Exception e) {
            log.error("save address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("save address error");
        }
    }

    /**
     * 更新地址信息
     *
     * @param zeroAddress 地址信息
     * @return 更新状态
     * @apiNote 更新地址信息
     */
    @OperateLog(operateModule = "地址模块", operateType = "更新", operateDesc = "更新地址")
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

    /**
     * 删除地址信息
     *
     * @param zeroAddress 地址信息
     * @return 删除状态
     * @apiNote 删除地址信息
     */
    @OperateLog(operateModule = "地址模块", operateType = "删除", operateDesc = "删除地址")
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
//    @GetMapping("test")
//    public ResponseEntity<Object> test() {
//        String localFilePath = "/Users/snail/Desktop/logo.png";
//        File localFile = new File(localFilePath);
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(localFile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        TencentFileUtils.upload(
//                environment.getProperty("tencent.cos.bucket-name"),
//                environment.getProperty("tencent.cos.secret-id"),
//                environment.getProperty("tencent.cos.secret-key"),
//                environment.getProperty("tencent.cos.region"),
//                "logo1.png",
//                inputStream
//        );
//        TencentFileUtils.generatePreSignedUrl(
//                environment.getProperty("tencent.cos.bucket-name"),
//                environment.getProperty("tencent.cos.secret-id"),
//                environment.getProperty("tencent.cos.secret-key"),
//                environment.getProperty("tencent.cos.region"),
//                "logo1.png"
//        );
//        return null;
//    }
}
