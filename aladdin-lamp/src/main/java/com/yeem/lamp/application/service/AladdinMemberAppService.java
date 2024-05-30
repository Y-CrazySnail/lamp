package com.yeem.lamp.application.service;

import com.yeem.lamp.infrastructure.persistence.service.IAladdinMemberService;
import com.yeem.lamp.presentation.request.MemberLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AladdinMemberAppService {

    @Autowired
    private IAladdinMemberService aladdinMemberService;

    public String login(MemberLoginRequest memberLoginRequest) {
        return null;
    }
}
