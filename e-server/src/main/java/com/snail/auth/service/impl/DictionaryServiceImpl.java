package com.snail.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.auth.mapper.DictionaryMapper;
import com.snail.auth.entity.Dictionary;
import com.snail.auth.service.IDictionaryService;
import org.springframework.stereotype.Service;

@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

}
