package com.yeem.lamp.presentation.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.yeem.lamp.application.dto.MemberDTO;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.application.service.MemberAppService;
import com.yeem.lamp.application.service.ServerAppService;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.application.service.XUIAppService;
import com.yeem.lamp.domain.entity.NodeVmess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private XUIAppService xuiAppService;
    @Autowired
    private ServerAppService serverAppService;
    @Autowired
    private ServiceAppService serviceAppService;
    @Autowired
    private MemberAppService memberAppService;

    @GetMapping("/reset")
    public String reset() {
        log.info("begin-sync local service status---------->");
        serviceAppService.syncStatus();
        log.info("end-sync local service status---------->");

        log.info("begin-sync local node status---------->");
        serverAppService.syncNode();
        log.info("end-sync local node status---------->");

        log.info("begin-sync reset remote node status---------->");
        xuiAppService.reset();
        log.info("end-sync reset remote node status---------->");
        return "ok";
    }

    @GetMapping("/sync")
    public String sync() {
        log.info("begin-sync remote data traffic---------->");
        xuiAppService.syncRemoteDataTraffic();
        log.info("end-sync remote data traffic---------->");

        log.info("begin-sync local service status---------->");
        serviceAppService.syncStatus();
        log.info("end-sync local service status---------->");

        log.info("begin-sync local node status---------->");
        serverAppService.syncNode();
        log.info("end-sync local node status---------->");

        log.info("begin-sync remote node status---------->");
        xuiAppService.syncRemoteNode();
        log.info("end-sync remote node status---------->");
        return "ok";
    }

    @GetMapping("/clash/{uuid}")
    public String clash(@PathVariable("uuid") String uuid) {
        List<String> nodeUrlList = getNodeUrlList(uuid);
        if (null == nodeUrlList || nodeUrlList.isEmpty()) {
            return null;
        }
        String url = String.join("|", nodeUrlList);
        String sub = "http://127.0.0.1:25500/sub?target=clash&url=" + URLEncodeUtil.encode(url);
        return HttpUtil.createRequest(Method.GET, sub).execute().body();
    }

    @GetMapping("/shadowrocket/{uuid}")
    public String shadowrocket(@PathVariable("uuid") String uuid) {
        List<String> nodeUrlList = getNodeUrlList(uuid);
        if (null == nodeUrlList || nodeUrlList.isEmpty()) {
            return null;
        }
        String url = String.join("|", nodeUrlList);
        String sub = "http://127.0.0.1:25500/sub?target=v2ray&url=" + URLEncodeUtil.encode(url);
        return HttpUtil.createRequest(Method.GET, sub).execute().body();
    }

    private List<String> getNodeUrlList(String uuid) {
        ServiceDTO serviceDTO = serviceAppService.getByUUID(uuid);
        if (Objects.isNull(serviceDTO)) {
            return null;
        }
        MemberDTO memberDTO = memberAppService.getById(serviceDTO.getMemberId());
        if (Objects.isNull(memberDTO)) {
            return null;
        }
        log.info("会员更新订阅：微信{}，邮箱：{}", memberDTO.getWechat(), memberDTO.getEmail());
        memberDTO.setLastUpdateSubscription(new Date());
        memberAppService.updateById(memberDTO);
        List<NodeVmess> nodeVmessList = serviceAppService.list(serviceDTO.getId(), DateUtil.beginOfDay(new Date()).toJdkDate());
        List<String> nodeUrlList = nodeVmessList.stream().map(NodeVmess::convert).collect(Collectors.toList());

        NodeVmess nodeVmessDoForTime = new NodeVmess();
        nodeVmessDoForTime.setNodePs("到期:" + DateUtil.format(serviceDTO.getEndDate(), DatePattern.NORM_DATE_PATTERN));
        nodeVmessDoForTime.setNodeAdd("google.com");
        nodeVmessDoForTime.setNodePort("443");
        nodeVmessDoForTime.setNodeId("00000000-0000-0000-0000-000000000000");
        nodeVmessDoForTime.setAid("0");
        nodeVmessDoForTime.setNet("tcp");
        nodeVmessDoForTime.setType("none");
        nodeVmessDoForTime.setTls("none");
        nodeUrlList.add(nodeVmessDoForTime.convert());
        NodeVmess nodeVmessDoForSurplus = new NodeVmess();
        nodeVmessDoForSurplus.setNodePs("本月剩余:" + serviceDTO.getSurplus() + "GB" + (serviceDTO.getSurplus().contains("-") ? "-已超额" : ""));
        nodeVmessDoForSurplus.setNodeAdd("google.com");
        nodeVmessDoForSurplus.setNodePort("443");
        nodeVmessDoForSurplus.setNodeId("00000000-0000-0000-0000-000000000000");
        nodeVmessDoForSurplus.setAid("0");
        nodeVmessDoForSurplus.setNet("tcp");
        nodeVmessDoForSurplus.setType("none");
        nodeVmessDoForSurplus.setTls("none");
        nodeUrlList.add(nodeVmessDoForSurplus.convert());
        NodeVmess nodeVmessDoForWebsite = new NodeVmess();
        nodeVmessDoForWebsite.setNodePs("官网: aladdinslamp.cc");
        nodeVmessDoForWebsite.setNodeAdd("google.com");
        nodeVmessDoForWebsite.setNodePort("443");
        nodeVmessDoForWebsite.setNodeId("00000000-0000-0000-0000-000000000000");
        nodeVmessDoForWebsite.setAid("0");
        nodeVmessDoForWebsite.setNet("tcp");
        nodeVmessDoForWebsite.setType("none");
        nodeVmessDoForWebsite.setTls("none");
        nodeUrlList.add(nodeVmessDoForWebsite.convert());
        return nodeUrlList;
    }
}
