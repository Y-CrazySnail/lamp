package com.yeem.lamp.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.entity.AladdinNodeVmess;
import com.yeem.lamp.entity.AladdinService;
import com.yeem.lamp.service.*;
import com.yeem.lamp.service.impl.XUIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private IAladdinMemberService aladdinMemberService;
    @Autowired
    private IAladdinServiceService aladdinServiceService;
    @Autowired
    private IAladdinNodeVmessService aladdinNodeVmessService;

    @Autowired
    private XUIService xuiService;

    @GetMapping("/clash/{uuid}")
    public String clash(@PathVariable("uuid") String uuid) {
        int year = DateUtil.year(new Date());
        int month = DateUtil.month(new Date()) + 1;
        AladdinService aladdinService = aladdinServiceService.getByUUID(uuid);
        if (Objects.isNull(aladdinService)) {
            return null;
        }
        AladdinMember aladdinMember = aladdinMemberService.getById(aladdinService.getMemberId());
        if (Objects.isNull(aladdinMember)) {
            return null;
        }
        log.info("会员更新clash订阅：微信{}，邮箱：{}", aladdinMember.getWechat(), aladdinMember.getEmail());
        aladdinMember.setLastUpdateSubscription(new Date());
        aladdinMemberService.updateById(aladdinMember);
        List<AladdinService> aladdinServiceList = aladdinServiceService.listValidByMemberId(aladdinMember.getId());
        List<String> nodeUrlList = new ArrayList<>();
        String endDate = endDate = DateUtil.format(aladdinService.getEndDate(), DatePattern.NORM_DATE_PATTERN);
        if (aladdinService.getDataTraffic() < 100) {
            List<AladdinNodeVmess> aladdinNodeVmessList = aladdinNodeVmessService.listByServiceId(aladdinService.getId(), year, month);
            Long up = 0L;
            Long down = 0L;
            for (AladdinNodeVmess aladdinNodeVmess : aladdinNodeVmessList) {
                up += aladdinNodeVmess.getServiceUp();
                down += aladdinNodeVmess.getServiceDown();
            }
            Double total = (double) ((up + down) / 1024 / 1024 / 1024);
            if (total > aladdinService.getDataTraffic()) {
                log.warn("流量已耗尽---memberId:{}, serviceId:{}", aladdinMember.getId(), aladdinService.getId());
            }
            String surplus = String.format("%.2f", aladdinService.getDataTraffic() - total);
            for (AladdinNodeVmess aladdinNodeVmess : aladdinNodeVmessList) {
                if (aladdinNodeVmess.getNodeType().equals(Constant.NODE_TYPE_PRIVATE)) {
                    aladdinNodeVmess.setNodePs(aladdinNodeVmess.getNodePs() + surplus + "GB");
                    nodeUrlList.add(aladdinNodeVmess.convert());
                }
            }
        } else {
            List<AladdinNodeVmess> aladdinNodeVmessList = aladdinNodeVmessService.listByNodeType(Constant.NODE_TYPE_PUBLIC);
            for (AladdinNodeVmess aladdinNodeVmess : aladdinNodeVmessList) {
                nodeUrlList.add(aladdinNodeVmess.convert());
            }
        }
        AladdinNodeVmess aladdinNodeVmess = new AladdinNodeVmess();
        aladdinNodeVmess.setNodePs(endDate + "-到期");
        aladdinNodeVmess.setNodeAdd("google.com");
        aladdinNodeVmess.setNodePort("443");
        aladdinNodeVmess.setNodeId("00000000-0000-0000-0000-000000000000");
        aladdinNodeVmess.setAid("0");
        aladdinNodeVmess.setNet("tcp");
        aladdinNodeVmess.setType("none");
        aladdinNodeVmess.setTls("none");
        nodeUrlList.add(aladdinNodeVmess.convert());
        String url = String.join("|", nodeUrlList);
        log.info("URL:{}", url);
        String sub = "http://127.0.0.1:25500/sub?target=clash&url=" + URLEncodeUtil.encode(url);
        return HttpUtil.createRequest(Method.GET, sub).execute().body();
    }

    @GetMapping("/shadowrocket/{uuid}")
    public String shadowrocket(@PathVariable("uuid") String uuid) {
        int year = DateUtil.year(new Date());
        int month = DateUtil.month(new Date()) + 1;
        AladdinService aladdinService = aladdinServiceService.getByUUID(uuid);
        if (Objects.isNull(aladdinService)) {
            return null;
        }
        AladdinMember aladdinMember = aladdinMemberService.getById(aladdinService.getMemberId());
        if (Objects.isNull(aladdinMember)) {
            return null;
        }
        log.info("会员更新shadowrocket订阅：微信{}，邮箱：{}", aladdinMember.getWechat(), aladdinMember.getEmail());
        aladdinMember.setLastUpdateSubscription(new Date());
        aladdinMemberService.updateById(aladdinMember);
        List<AladdinService> aladdinServiceList = aladdinServiceService.listValidByMemberId(aladdinMember.getId());
        List<String> nodeUrlList = new ArrayList<>();
        String endDate = DateUtil.format(aladdinService.getEndDate(), DatePattern.NORM_DATE_PATTERN);
        if (aladdinService.getDataTraffic() < 100) {
            List<AladdinNodeVmess> aladdinNodeVmessList = aladdinNodeVmessService.listByServiceId(aladdinService.getId(), year, month);
            Long up = 0L;
            Long down = 0L;
            for (AladdinNodeVmess aladdinNodeVmess : aladdinNodeVmessList) {
                up += aladdinNodeVmess.getServiceUp();
                down += aladdinNodeVmess.getServiceDown();
            }
            Double total = (double) ((up + down) / 1024 / 1024 / 1024);
            if (total > aladdinService.getDataTraffic()) {
                log.warn("流量已耗尽---memberId:{}, serviceId:{}", aladdinMember.getId(), aladdinService.getId());
                return null;
            }
            String surplus = String.format("%.2f", aladdinService.getDataTraffic() - total);
            for (AladdinNodeVmess aladdinNodeVmess : aladdinNodeVmessList) {
                if (aladdinNodeVmess.getNodeType().equals(Constant.NODE_TYPE_PRIVATE)) {
                    aladdinNodeVmess.setNodePs(aladdinNodeVmess.getNodePs() + surplus + "GB");
                    nodeUrlList.add(aladdinNodeVmess.convert());
                }
            }
        } else {
            List<AladdinNodeVmess> aladdinNodeVmessList = aladdinNodeVmessService.listByNodeType(Constant.NODE_TYPE_PUBLIC);
            for (AladdinNodeVmess aladdinNodeVmess : aladdinNodeVmessList) {
                nodeUrlList.add(aladdinNodeVmess.convert());
            }
        }
        AladdinNodeVmess aladdinNodeVmess = new AladdinNodeVmess();
        aladdinNodeVmess.setNodePs(endDate + "-到期");
        aladdinNodeVmess.setNodeAdd("google.com");
        aladdinNodeVmess.setNodePort("443");
        aladdinNodeVmess.setNodeId("00000000-0000-0000-0000-000000000000");
        aladdinNodeVmess.setAid("0");
        aladdinNodeVmess.setNet("tcp");
        aladdinNodeVmess.setType("none");
        aladdinNodeVmess.setTls("none");
        nodeUrlList.add(aladdinNodeVmess.convert());
        String url = String.join("|", nodeUrlList);
        log.info("URL:{}", url);
        String sub = "http://127.0.0.1:25500/sub?target=v2ray&url=" + URLEncodeUtil.encode(url);
        return HttpUtil.createRequest(Method.GET, sub).execute().body();
    }
}
