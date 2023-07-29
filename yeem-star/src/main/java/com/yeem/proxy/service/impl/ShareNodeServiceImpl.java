package com.yeem.proxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.proxy.entity.ShareNode;
import com.yeem.proxy.mapper.ShareNodeMapper;
import com.yeem.proxy.service.IShareNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ShareNodeServiceImpl extends ServiceImpl<ShareNodeMapper, ShareNode> implements IShareNodeService {

}
