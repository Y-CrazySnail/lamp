package com.snail.chinaybop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.chinaybop.entity.Dictionary;
import com.snail.chinaybop.mapper.DictionaryMapper;
import com.snail.chinaybop.service.IDictionaryService;
import org.springframework.stereotype.Service;

@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

}
