package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.CFQuality;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ICFQualityService extends IService<CFQuality> {
    List<CFQuality> list(String tenantNo, String queryKey);
}
