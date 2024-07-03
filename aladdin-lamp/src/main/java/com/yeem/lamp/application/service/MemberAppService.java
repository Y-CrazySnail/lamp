package com.yeem.lamp.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.MemberDTO;
import com.yeem.lamp.application.dto.TokenDTO;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.Token;
import com.yeem.lamp.domain.service.MemberDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import com.yeem.lamp.infrastructure.exception.YeemException;
import com.yeem.lamp.infrastructure.message.TelegramMessage;
import com.yeem.lamp.presentation.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberAppService {

    @Autowired
    private MemberDomainService memberDomainService;
    @Autowired
    private ServiceDomainService serviceDomainService;
    @Autowired
    private TelegramMessage telegramMessage;

    public MemberDTO getByIdWithService(Long id) {
        Member member = memberDomainService.getById(id);
        MemberDTO memberDTO = MemberDTO.init(member);
        List<Services> servicesList = serviceDomainService.listByMemberId(id);
        memberDTO.setServicesList(servicesList);
        return memberDTO;
    }

    public List<MemberDTO> list(MemberDTO memberDTO) {
        Member member = memberDTO.convertMember();
        List<Member> memberList = memberDomainService.list(member);
        return memberList.stream().map(MemberDTO::init).collect(Collectors.toList());
    }

    public IPage<MemberDTO> pages(int current, int size, String email, String wechat) {
        IPage<Member> page = memberDomainService.pages(current, size, email, wechat);
        IPage<MemberDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(MemberDTO::init).collect(Collectors.toList()));
        return pageDTO;
    }

    public MemberDTO getById(Long id) {
        Member member = memberDomainService.getById(id);
        return MemberDTO.init(member);
    }

    public void updateById(MemberDTO memberDTO) {
        Member member = memberDTO.convertMember();
        memberDomainService.updateById(member);
    }

    public void save(MemberDTO memberDTO) {
        Member member = memberDTO.convertMember();
        memberDomainService.save(member);
    }

    public void removeById(Long id) {
        memberDomainService.removeById(id);
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
