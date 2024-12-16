package com.lamp.application.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.application.dto.MemberDTO;
import com.lamp.domain.entity.Member;
import com.lamp.domain.service.manage.MemberManageDomainService;
import com.lamp.domain.service.manage.ServiceManageDomainService;
import com.lamp.infrastructure.message.TelegramMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberManageAppService {

    @Autowired
    private MemberManageDomainService memberDomainService;
    @Autowired
    private ServiceManageDomainService serviceDomainService;
    @Autowired
    private TelegramMessage telegramMessage;

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
        serviceDomainService.removeByMemberId(id);
    }
}
