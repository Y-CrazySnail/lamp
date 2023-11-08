package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.lamp.entity.ShareNode;
import com.yeem.lamp.mapper.ShareNodeMapper;
import com.yeem.lamp.service.IShareNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ShareNodeServiceImpl extends ServiceImpl<ShareNodeMapper, ShareNode> implements IShareNodeService {

}
