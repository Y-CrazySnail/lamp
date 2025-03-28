package com.lamp.service.web;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.im.dto.SysTelegramSendDTO;
import com.lamp.im.service.ISysTelegramService;
import com.lamp.mapper.LampMemberMapper;
import com.lamp.security.LampToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class LampMemberService extends ServiceImpl<LampMemberMapper, LampMember> {

    @Autowired
    private LampMemberMapper memberMapper;

    @Autowired
    private LampRewardRecordService rewardRecordService;

    @Autowired
    private LampOrderService orderService;

    @Autowired
    private ISysTelegramService sysTelegramService;

    @Override
    public LampMember getById(Serializable id) {
        LampMember member = memberMapper.selectById(id);
        rewardRecordService.setRewardRecordList(member);
        orderService.setOrderList(member);
        return member;
    }

    public void updatePassword(Long id, String password) {
        LambdaUpdateWrapper<LampMember> updateWrapper = new LambdaUpdateWrapper<>(LampMember.class);
        updateWrapper.set(LampMember::getPassword, password);
        updateWrapper.eq(LampMember::getId, id);
        memberMapper.update(null, updateWrapper);
    }

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
        SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
        sysTelegramSendDTO.setTemplateName("login");
        sysTelegramSendDTO.setTemplateType("telegram");
        Map<String, Object> replaceMap = new HashMap<>();
        replaceMap.put("email", member.getEmail());
        sysTelegramSendDTO.setReplaceMap(replaceMap);
        sysTelegramService.send(sysTelegramSendDTO);
        return member;
    }

    public String signup(LampMember member) {
        LampMember referrer = getByReferralCode(member.getReferrerCode());
        if (Objects.isNull(referrer)) {
            log.error("推荐码无效！");
            return "推荐码无效";
        }
        if (!Validator.isEmail(member.getUsername())) {
            log.error("邮箱格式错误！");
            return "邮箱格式错误";
        }
        LampMember exist = getByEmail(member.getUsername());
        if (Objects.nonNull(exist)) {
            return "邮箱已注册";
        }
        member.setEmail(member.getUsername());
        member.setUuid(UUID.fastUUID().toString());
        member.setBandwidth(0L);
        member.setLastSyncTime(LocalDateTime.now());
        member.setSignupTime(LocalDateTime.now());
        member.setExpiryDate(LocalDate.now().minusDays(1));
        member.setLevel(1);
        generateCode(member);
        member.setRemark("");
        member.setBalance(new BigDecimal(0));
        member.setMonthBandwidth(0L);
        member.setMonthBandwidthUp(0L);
        member.setMonthBandwidthDown(0L);
        save(member);
        SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
        sysTelegramSendDTO.setTemplateName("signup");
        sysTelegramSendDTO.setTemplateType("telegram");
        Map<String, Object> replaceMap = new HashMap<>();
        replaceMap.put("email", member.getEmail());
        sysTelegramSendDTO.setReplaceMap(replaceMap);
        sysTelegramService.send(sysTelegramSendDTO);
        return null;
    }

    public LampMember getByUUID(String uuid) {
        LambdaQueryWrapper<LampMember> queryWrapper = new LambdaQueryWrapper<>(LampMember.class);
        queryWrapper.eq(LampMember::getUuid, uuid);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return getOne(queryWrapper);
    }

    public LampMember getByReferralCode(String referralCode) {
        LambdaQueryWrapper<LampMember> queryWrapper = new LambdaQueryWrapper<>(LampMember.class);
        queryWrapper.eq(LampMember::getReferralCode, referralCode);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return getOne(queryWrapper);
    }

    public LampMember getByEmail(String email) {
        LambdaQueryWrapper<LampMember> queryWrapper = new LambdaQueryWrapper<>(LampMember.class);
        queryWrapper.eq(LampMember::getEmail, email);
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
