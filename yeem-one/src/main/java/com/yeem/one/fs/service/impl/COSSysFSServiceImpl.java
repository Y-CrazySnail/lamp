package com.yeem.one.fs.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.utils.OauthUtils;
import com.yeem.common.utils.TencentFileUtils;
import com.yeem.one.fs.entity.SysFS;
import com.yeem.one.fs.mapper.SysFSMapper;
import com.yeem.one.fs.service.ISysFSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class COSSysFSServiceImpl extends ServiceImpl<SysFSMapper, SysFS> implements ISysFSService {

    public static final String APPLICATION = "yeem_one";

    @Autowired
    private Environment environment;
    @Autowired
    private SysFSMapper mapper;

    @Override
    public String upload(SysFS sysFS, MultipartFile file) {
        int year = DateUtil.year(new Date());
        String dateStr = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String key = APPLICATION;
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
