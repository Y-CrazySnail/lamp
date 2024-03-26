package com.yeem.fss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.fss.entity.SysFS;
import org.springframework.web.multipart.MultipartFile;

public interface ISysFSService extends IService<SysFS> {
    String upload(String application, SysFS sysFS, MultipartFile file);
}
