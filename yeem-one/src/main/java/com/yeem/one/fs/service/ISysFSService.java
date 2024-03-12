package com.yeem.one.fs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.fs.entity.SysFS;
import org.springframework.web.multipart.MultipartFile;

public interface ISysFSService extends IService<SysFS> {
    String upload(SysFS sysFS, MultipartFile file);
}
