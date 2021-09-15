package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Dictionary;
import com.snail.mapper.DictionaryMapper;
import com.snail.service.IDictionaryService;
import org.springframework.stereotype.Service;

@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

}
