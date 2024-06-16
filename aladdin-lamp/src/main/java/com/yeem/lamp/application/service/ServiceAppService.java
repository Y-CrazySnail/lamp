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
import com.yeem.lamp.domain.objvalue.Server;
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


    public ServiceDTO getById(Long id) {
        Services services = serviceDomainService.getById(id);
        return new ServiceDTO(services);
    }

    public ServiceDTO getByUUID(String uuid) {
        Services services = serviceDomainService.getByUUID(uuid);
        return new ServiceDTO(services);
    }

    public IPage<ServiceDTO> pages(int current, int size,
                                   Long memberId, String status, String wechat, String email) {
        IPage<Services> page = serviceDomainService.pages(current, size, memberId, status, wechat, email);
        IPage<ServiceDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(ServiceDTO::new).collect(Collectors.toList()));
        return pageDTO;
    }

    public List<ServiceDTO> listByMemberId(Long memberId) {
        List<Services> servicesList = serviceDomainService.listByMemberId(memberId);
        return servicesList.stream().peek(Services::dealSurplus).map(ServiceDTO::new).collect(Collectors.toList());
    }

    public void save(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.save(services);
    }

    public void updateById(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.updateById(services);
    }

    public void updateUUID(Long memberId, ServiceDTO serviceDTO) {
        serviceDomainService.updateUUID(memberId, serviceDTO.getId(), serviceDTO.getUuid());
    }

    public void removeById(Long id) {
        serviceDomainService.removeById(id);
    }

    public String clash(String uuid) {
        Services services = serviceDomainService.getByUUID(uuid);
        if (Objects.isNull(services)) {
            return null;
        }
        Member member = memberDomainService.getById(services.getMemberId());
        if (Objects.isNull(member)) {
            return null;
        }
        log.info("会员更新订阅：微信{}，邮箱：{}", member.getWechat(), member.getEmail());
        member.setLastUpdateSubscription(new Date());
        memberDomainService.updateById(member);
        List<NodeVmess> nodeVmessList = serviceDomainService.listNodeVmess(uuid);

        String endDateStr = "到期:" + DateUtil.format(services.getEndDate(), DatePattern.NORM_DATE_PATTERN);
        NodeVmess nodeVmessDoForTime = NodeVmess.initInformation(endDateStr);
        nodeVmessList.add(nodeVmessDoForTime);

        services.dealSurplus();
        String surplusStr = "本月剩余:" + services.getSurplus() + "GB";
        NodeVmess nodeVmessDoForSurplus = NodeVmess.initInformation(surplusStr);
        nodeVmessList.add(nodeVmessDoForSurplus);

        String websiteStr = "官网:aladdinslamp.cc";
        NodeVmess nodeVmessDoForWebsite = NodeVmess.initInformation(websiteStr);
        nodeVmessList.add(nodeVmessDoForWebsite);

        SpringTemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, "classpath:template");
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setTemplateLoader(templateLoader);
        Template template;
        String sub = null;
        try {
            template = configuration.getTemplate("clash.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("nodeList", nodeVmessList);
            sub = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return sub;
    }

    public String v2ray(String uuid) {
        Services services = serviceDomainService.getByUUID(uuid);
        if (Objects.isNull(services)) {
            return null;
        }
        Member member = memberDomainService.getById(services.getMemberId());
        if (Objects.isNull(member)) {
            return null;
        }
        log.info("会员更新订阅：微信{}，邮箱：{}", member.getWechat(), member.getEmail());
        member.setLastUpdateSubscription(new Date());
        memberDomainService.updateById(member);
        List<NodeVmess> nodeVmessList = serviceDomainService.listNodeVmess(uuid);

        String endDateStr = "到期:" + DateUtil.format(services.getEndDate(), DatePattern.NORM_DATE_PATTERN);
        NodeVmess nodeVmessDoForTime = NodeVmess.initInformation(endDateStr);
        nodeVmessList.add(nodeVmessDoForTime);

        BigDecimal gb = new BigDecimal(Services.GB);
        BigDecimal surplus = BigDecimal.valueOf(services.getDataTraffic())
                .subtract(BigDecimal.valueOf(services.getServiceUp()).divide(gb, RoundingMode.HALF_UP))
                .subtract(BigDecimal.valueOf(services.getServiceDown()).divide(gb, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
        String surplusStr = "本月剩余:" + surplus + "GB";
        NodeVmess nodeVmessDoForSurplus = NodeVmess.initInformation(surplusStr);
        nodeVmessList.add(nodeVmessDoForSurplus);

        String websiteStr = "官网:aladdinslamp.cc";
        NodeVmess nodeVmessDoForWebsite = NodeVmess.initInformation(websiteStr);
        nodeVmessList.add(nodeVmessDoForWebsite);

        SpringTemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, "classpath:template");
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setTemplateLoader(templateLoader);
        Template template;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            template = configuration.getTemplate("v2ray.ftl");
            for (NodeVmess nodeVmess : nodeVmessList) {
                stringBuilder.append("vmess://")
                        .append(Base64.encode(FreeMarkerTemplateUtils.processTemplateIntoString(template, nodeVmess)))
                        .append("\n");
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return Base64.encode(stringBuilder.toString());
    }
}
