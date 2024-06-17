package com.yeem.lamp.application.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.service.MemberDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceAppService {

    @Autowired
    private MemberDomainService memberDomainService;
    @Autowired
    private ServiceDomainService serviceDomainService;
    @Autowired
    private ResourceLoader resourceLoader;

    public ServiceDTO getServiceById(Long id) {
        Services services = serviceDomainService.getById(id);
        return new ServiceDTO(services);
    }

    public List<ServiceDTO> listServiceByMemberId(Long memberId) {
        List<Services> servicesList = serviceDomainService.listByMemberId(memberId);
        return servicesList.stream().peek(Services::dealSurplus).map(ServiceDTO::new).collect(Collectors.toList());
    }

    public IPage<ServiceDTO> pageService(int current, int size, Long memberId, String status, String wechat, String email) {
        IPage<Services> page = serviceDomainService.pages(current, size, memberId, status, wechat, email);
        IPage<ServiceDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(ServiceDTO::new).collect(Collectors.toList()));
        return pageDTO;
    }

    public void saveService(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.save(services);
    }

    public void updateServiceById(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.updateById(services);
    }

    public void removeServiceById(Long id) {
        serviceDomainService.removeById(id);
    }

    public String clash(String uuid) {
        return serviceDomainService.clash(uuid);
    }

    public String v2ray(String uuid) {
        return serviceDomainService.v2ray(uuid);
    }
}
