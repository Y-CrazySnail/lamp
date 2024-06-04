package com.yeem.lamp.application.service;

import cn.hutool.core.thread.ThreadUtil;
import com.yeem.lamp.application.dto.MemberDTO;
import com.yeem.lamp.application.dto.TokenDTO;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.factory.MemberFactory;
import com.yeem.lamp.domain.objvalue.Token;
import com.yeem.lamp.domain.service.MemberDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import com.yeem.lamp.infrastructure.exception.YeemException;
import com.yeem.lamp.infrastructure.message.TelegramMessage;
import com.yeem.lamp.presentation.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberAppService {

    @Autowired
    private MemberDomainService memberDomainService;
    @Autowired
    private ServiceDomainService serviceDomainService;
    @Autowired
    private MemberFactory memberFactory;
    @Autowired
    private TelegramMessage telegramMessage;

    public TokenDTO login(LoginRequest loginRequest) {
        Member member = memberFactory.create(loginRequest);
        Token token = memberDomainService.login(member);
        if (token == null) {
            throw new YeemException("login error");
        }
        telegramMessage.sendLoginNotice(member);
        return new TokenDTO(token);
    }

    public MemberDTO getByIdWithService(Long id) {
        Member member = memberDomainService.getById(id);
        MemberDTO memberDTO = new MemberDTO(member);
        List<com.yeem.lamp.domain.entity.Service> serviceList = serviceDomainService.listByMemberId(id);
        memberDTO.setServiceList(serviceList);
        return memberDTO;
    }
}
