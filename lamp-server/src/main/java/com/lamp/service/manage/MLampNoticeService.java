package com.lamp.service.manage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.entity.LampNotice;
import com.lamp.mapper.LampNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MLampNoticeService extends ServiceImpl<LampNoticeMapper, LampNotice> {

    @Autowired
    private LampNoticeMapper noticeMapper;

}
