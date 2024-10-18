package com.yeem.car.controller.wechat;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.car.config.Constant;
import com.yeem.car.entity.*;
import com.yeem.car.security.WechatAuthInterceptor;
import com.yeem.car.service.*;
import com.yeem.common.utils.TencentFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private ICarBrandService carBrandService;
    @Autowired
    private ICarLevelService carLevelService;
    @Autowired
    private ICarFilmProductService carFilmProductService;
    @Autowired
    private ICarFilmQualityService carFilmQualityService;
    @Autowired
    private ICarFilmUserService carFilmUserService;
    @Autowired
    private ICarFilmMessageService carFilmMessageService;
    @Autowired
    private ICarDictionaryService carDictionaryService;
    @Autowired
    private ICarFilmStoreService carFilmStoreService;




    @Value("${tencent.cos.bucket-name}")
    private String TENCENT_COS_BUCKET_NAME;
    @Value("${tencent.cos.secret-id}")
    private String TENCENT_COS_SECRET_ID;
    @Value("${tencent.cos.secret-key}")
    private String TENCENT_COS_SECRET_KEY;
    @Value("${tencent.cos.region}")
    private String TENCENT_COS_REGION;



    

    /**
     * 查询小程序基础信息
     *
     * @param productNo
     * @return
     */
    @GetMapping("/getBaseInfo")
    public ResponseEntity<Object> getBaseInfo(@RequestParam(value = "productNo") String productNo) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<BaseCarBrand> baseCarBrandList = carBrandService.list();
            result.put("brandList", baseCarBrandList);
            List<CarFilmProduct> carFilmProductList = carFilmProductService.listWithPrice(productNo);
            result.put("productList", carFilmProductList);
            List<BaseCarDictionary> carDictionaryList = carDictionaryService.list(null, productNo, null);
            result.put("dictionaryList", carDictionaryList);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
    }

    /**
     * 查询质保信息
     *
     * @param productNo 产品代码
     * @param queryKey  查询关键字
     * @return 质保信息列表
     */
    @GetMapping("getQualityInfo")
    public ResponseEntity<Object> getQualityInfo(@RequestParam(value = "productNo") String productNo,
                                                 @RequestParam(value = "queryKey") String queryKey) {
        if (StrUtil.isEmpty(queryKey)) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
        try {
            List<CarFilmQuality> carFilmQualityList = carFilmQualityService.getQualityInfo(productNo, queryKey);
            return ResponseEntity.ok(carFilmQualityList);
        } catch (Exception e) {
            log.error("查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
    }

    @PostMapping("saveQualityInfo")
    public ResponseEntity<Object> saveQualityInfo(@RequestBody CarFilmQuality carFilmQuality) {
        Long userId = WechatAuthInterceptor.getUserId();
        if (null == userId) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        CarFilmUser carFilmUser = carFilmUserService.getById(userId);
        if (!Constant.TRUE_STRING.equals(carFilmUser.getQualityPermission())) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        try {
            carFilmQualityService.save(carFilmQuality);
            return ResponseEntity.ok("录入质保成功");
        } catch (Exception e) {
            log.error("input quality info error", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("录入质保失败");
        }
    }

    @PostMapping("saveStoreInfo")
    public ResponseEntity<Object> saveStoreInfo(@RequestBody CarFilmStore carFilmStore) {
        Long userId = WechatAuthInterceptor.getUserId();
        if (null == userId) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        CarFilmUser carFilmUser = carFilmUserService.getById(userId);
        if (!Constant.TRUE_STRING.equals(carFilmUser.getQualityPermission())) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        try {
            carFilmStoreService.save(carFilmStore);
            return ResponseEntity.ok("录入店铺成功");
        } catch (Exception e) {
            log.error("input quality info error", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("录入质保失败");
        }
    }

    /**
     * 小程序登录
     *
     * @return 登录信息
     * @apiNote 小程序登录
     */
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody CarFilmUser carFilmUser) {
        try {
            return ResponseEntity.ok(carFilmUserService.login(carFilmUser));
        } catch (Exception e) {
            log.error("wx login api error：", e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("updateUserInfo")
    public ResponseEntity<Object> update(@RequestBody CarFilmUser carFilmUser) {
        Long userId = WechatAuthInterceptor.getUserId();
        if (null == userId) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        carFilmUser.setId(userId);
        try {
            carFilmUserService.updateById(carFilmUser);
            return ResponseEntity.ok(carFilmUser);
        } catch (Exception e) {
            log.error("update user info error：", e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("message")
    public ResponseEntity<Object> message(@RequestBody CarFilmMessage carFilmMessage) {
        Long userId = WechatAuthInterceptor.getUserId();
        if (null == userId) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        QueryWrapper<CarFilmMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        int count = carFilmMessageService.count(queryWrapper);
        if (count > 5) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("留言失败");
        }
        carFilmMessage.setProductNo(WechatAuthInterceptor.getApplication());
        carFilmMessage.setDatetime(new Date());
        if (!StrUtil.isEmpty(carFilmMessage.getName())) {
            carFilmMessage.setName(filterChar(carFilmMessage.getName()));
        }
        if (!StrUtil.isEmpty(carFilmMessage.getPhone())) {
            carFilmMessage.setPhone(filterChar(carFilmMessage.getPhone()));
        }
        if (!StrUtil.isEmpty(carFilmMessage.getEmail())) {
            carFilmMessage.setEmail(filterChar(carFilmMessage.getEmail()));
        }
        if (!StrUtil.isEmpty(carFilmMessage.getContent())) {
            carFilmMessage.setContent(filterChar(carFilmMessage.getContent()));
        }
        carFilmMessage.setUserId(userId);
        carFilmMessageService.save(carFilmMessage);
        return ResponseEntity.ok("ok");
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

    private String filterChar(String content) {
        content = content.replace("$", "");
        content = content.replace("#", "");
        content = content.replace("{", "");
        content = content.replace("}", "");
        content = content.replace("script", "");
        content = content.replace("document", "");
        content = content.replace("select", "");
        content = content.replace("delete", "");
        content = content.replace("update", "");
        content = content.replace("insert", "");
        return content;
    }
}
