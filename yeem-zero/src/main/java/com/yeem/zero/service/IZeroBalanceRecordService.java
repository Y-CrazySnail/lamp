package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroBalanceRecord;
import com.yeem.zero.entity.ZeroOrder;

import java.util.List;

public interface IZeroBalanceRecordService extends IService<ZeroBalanceRecord> {
    List<ZeroBalanceRecord> listByUsername();
}
