package com.yeem.lamp.application.service;

import com.yeem.lamp.application.dto.TokenDTO;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.factory.MemberFactory;
import com.yeem.lamp.domain.objvalue.Token;
import com.yeem.lamp.domain.service.MemberDomainService;
import com.yeem.lamp.infrastructure.message.TelegramMessage;
import com.yeem.lamp.presentation.request.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberAppService {

    @Autowired
    private MemberDomainService memberDomainService;
    @Autowired
    private MemberFactory memberFactory;
    @Autowired
    private TelegramMessage telegramMessage;

    public TokenDTO login(MemberVO memberVO) {
        Member member = memberFactory.create(memberVO);
        Token token = memberDomainService.login(member);
        if (token == null) {
            throw new RuntimeException("");
        }
        telegramMessage.sendLoginNotice(member);
        return new TokenDTO(token);
    }
}
