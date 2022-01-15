package com.snail.chinaybop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.chinaybop.entity.ChinaybopMessage;
import com.snail.chinaybop.mapper.ChinaybopMessageMapper;
import com.snail.chinaybop.service.IChinaybopMessageService;
import org.springframework.stereotype.Service;

@Service
public class ChinaybopMessageServiceImpl extends ServiceImpl<ChinaybopMessageMapper, ChinaybopMessage> implements IChinaybopMessageService {

}
