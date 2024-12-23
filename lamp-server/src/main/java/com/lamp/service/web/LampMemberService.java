package com.lamp.service.web;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.mapper.LampMemberMapper;
import com.lamp.security.LampToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class LampMemberService extends ServiceImpl<LampMemberMapper, LampMember> {

    @Autowired
    private LampMemberMapper memberMapper;

    public LampMember login(LampMember member) {
        LambdaQueryWrapper<LampMember> queryWrapper = new LambdaQueryWrapper<>(LampMember.class);
        queryWrapper.eq(LampMember::getEmail, member.getUsername());
        queryWrapper.eq(LampMember::getPassword, member.getPassword());
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampMember> memberList = memberMapper.selectList(queryWrapper);
        if (memberList.isEmpty()) {
            log.error("登录错误：{}, {}", member.getEmail(), member.getPassword());
            throw new RuntimeException("登录错误");
        }
        member = memberList.get(0);
        LampToken token = new LampToken();
        token.setToken(member.getId());
        member.setToken(token.getToken());
        return member;
    }

    public boolean signUp(LampMember member) {
        member.setEmail(member.getUsername());
        member.setUuid(UUID.fastUUID().toString());
        member.setBandwidth(0L);
        member.setLastSyncTime(LocalDateTime.now());
        member.setExpiryDate(LocalDate.now().minusDays(1));
        member.setLevel(0);
        generateCode(member);
        member.setRemark("");
        member.setBalance(new BigDecimal(0));
        member.setMonthBandwidth(0L);
        member.setMonthBandwidthUp(0L);
        member.setMonthBandwidthDown(0L);
        return save(member);
    }

    public LampMember getByUUID(String uuid) {
        LambdaQueryWrapper<LampMember> queryWrapper = new LambdaQueryWrapper<>(LampMember.class);
        queryWrapper.eq(LampMember::getUuid, uuid);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return getOne(queryWrapper);
    }

    public void generateCode(LampMember member) {
        // 定义推荐码的字符集
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String referralCode = "";
        boolean isExist = true;
        while (isExist) {
            StringBuilder code = new StringBuilder();
            Random random = new Random();
            // 循环生成4位随机字符
            for (int i = 0; i < 4; i++) {
                int index = random.nextInt(characters.length());
                code.append(characters.charAt(index));
            }
            LambdaQueryWrapper<LampMember> queryWrapper = new LambdaQueryWrapper<>(LampMember.class);
            queryWrapper.eq(LampMember::getReferralCode, code.toString());
            BaseEntity.setDeleteFlagCondition(queryWrapper);
            isExist = memberMapper.selectCount(queryWrapper) > 0;
            if (!isExist) {
                referralCode = code.toString();
            }
        }
        member.setReferralCode(referralCode);
    }
}
