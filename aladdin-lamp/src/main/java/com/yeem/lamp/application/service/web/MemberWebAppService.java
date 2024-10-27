package com.yeem.lamp.application.service.web;

import com.yeem.lamp.application.dto.MemberDTO;
import com.yeem.lamp.application.dto.TokenDTO;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.Token;
import com.yeem.lamp.domain.service.web.MemberWebDomainService;
import com.yeem.lamp.domain.service.web.ServiceWebDomainService;
import com.yeem.lamp.infrastructure.exception.YeemException;
import com.yeem.lamp.infrastructure.message.TelegramMessage;
import com.yeem.lamp.presentation.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberWebAppService {

    @Autowired
    private MemberWebDomainService memberDomainService;
    @Autowired
    private ServiceWebDomainService serviceDomainService;
    @Autowired
    private TelegramMessage telegramMessage;

    public MemberDTO getByIdWithService(Long id) {
        Member member = memberDomainService.getById(id);
        MemberDTO memberDTO = MemberDTO.init(member);
        List<Services> servicesList = serviceDomainService.listByMemberId(id);
        memberDTO.setServicesList(servicesList);
        return memberDTO;
    }

    public TokenDTO login(LoginRequest loginRequest) {
        Member member = loginRequest.convertMember();
        Token token = memberDomainService.login(member);
        if (token == null) {
            throw new YeemException("login error");
        }
        telegramMessage.sendLoginNotice(member);
        return new TokenDTO(token);
    }

    public void updatePassword(Long id, String password) {

    }

    public void updateReferralCode(Long id, String referralCode) {

    }
}
