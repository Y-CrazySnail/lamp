package com.snail.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.auth.entity.UserExtra;
import com.snail.auth.mapper.UserExtraMapper;
import com.snail.auth.service.IUserExtraService;
import org.springframework.stereotype.Service;

@Service
public class UserExtraServiceImpl extends ServiceImpl<UserExtraMapper, UserExtra> implements IUserExtraService {

}
