package com.yeem.lamp.domain.factory;

import com.yeem.lamp.domain.entity.AladdinMember;
import com.yeem.lamp.presentation.request.MemberLoginRequest;
import org.springframework.stereotype.Component;

@Component
public class AladdinMemberFactory {
    public AladdinMember create(MemberLoginRequest memberLoginRequest) {
        AladdinMember aladdinMember = new AladdinMember();
        aladdinMember.setEmail(memberLoginRequest.getUsername());
        aladdinMember.setPassword(memberLoginRequest.getPassword());
        return aladdinMember;
    }
}
