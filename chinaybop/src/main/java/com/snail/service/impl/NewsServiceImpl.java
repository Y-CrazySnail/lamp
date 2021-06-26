package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.News;
import com.snail.mapper.NewsMapper;
import com.snail.service.INewsService;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
