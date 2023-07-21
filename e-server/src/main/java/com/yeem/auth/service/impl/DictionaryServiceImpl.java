package com.yeem.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.auth.mapper.DictionaryMapper;
import com.yeem.auth.entity.Dictionary;
import com.yeem.auth.service.IDictionaryService;
import org.springframework.stereotype.Service;

@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

}
