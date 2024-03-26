package com.yeem.fss.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.utils.OauthUtils;
import com.yeem.fss.util.TencentCOSUtils;
import com.yeem.fss.entity.SysFS;
import com.yeem.fss.service.ISysFSService;
import com.yeem.fss.mapper.SysFSMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class COSSysFSServiceImpl extends ServiceImpl<SysFSMapper, SysFS> implements ISysFSService {

    @Value("${tencent.secret-id}")
    private String TENCENT_SECRET_ID;
    @Value("${tencent.secret-key}")
    private String TENCENT_SECRET_KEY;
    @Value("${tencent.cos.bucket-name}")
    private String TENCENT_COS_BUCKET_NAME;
    @Value("${tencent.cos.region}")
    private String TENCENT_COS_REGION;

    @Autowired
    private SysFSMapper mapper;

    @Override
    public String upload(String application, SysFS sysFS, MultipartFile file) {
        int year = DateUtil.year(new Date());
        String dateStr = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String key = application;
        String username = OauthUtils.getUsername();
        if (StrUtil.isNotEmpty(username)) {
            key = key + "/" + username;
        }
        if (StrUtil.isEmpty(sysFS.getFsBusiness())) {
            log.warn("sys fs business is null, file:{}", file.getOriginalFilename());
            key += "/none_business";
        } else {
            key = key + "/" + sysFS.getFsBusiness();
        }
        key = key + "/" + year + "/" + dateStr + "_" + UUID.fastUUID() + "_" + file.getOriginalFilename();
        try {
            TencentCOSUtils.upload(TENCENT_COS_BUCKET_NAME, TENCENT_SECRET_ID, TENCENT_SECRET_KEY, TENCENT_COS_REGION, key, file.getInputStream());
            log.info("upload file to tencent cos, key:{}", key);
            String url = TencentCOSUtils.getUrl(TENCENT_COS_BUCKET_NAME, TENCENT_COS_REGION, key).toString();
            sysFS.setFsKey(key);
            sysFS.setFsUrl(url);
            sysFS.setFsType("cos");
            mapper.insert(sysFS);
            return url;
        } catch (IOException e) {
            log.error("upload file to tencent cos error:", e);
            return null;
        }
    }
}
