package com.yeem.lamp.domain.repository;

import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Service;

import java.util.List;

public interface ServiceRepository {
    List<Service> listByMemberId(Long memberId);
}
