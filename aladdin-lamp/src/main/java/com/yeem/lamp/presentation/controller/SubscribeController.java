package com.yeem.lamp.presentation.controller;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.yeem.lamp.infrastructure.persistence.repository.XUIService;
import com.yeem.lamp.infrastructure.x.XUIClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private XUIService xuiService;

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
//        int year = DateUtil.year(new Date());
//        int month = DateUtil.month(new Date()) + 1;
//        ServiceDo serviceDo = aladdinServiceService.getByUUID(uuid);
//        if (Objects.isNull(serviceDo)) {
//            return null;
//        }
//        MemberDo aladdinMember = aladdinMemberService.getById(serviceDo.getMemberId());
//        if (Objects.isNull(aladdinMember)) {
//            return null;
//        }
//        log.info("会员更新订阅：微信{}，邮箱：{}", aladdinMember.getWechat(), aladdinMember.getEmail());
//        aladdinMember.setLastUpdateSubscription(new Date());
//        aladdinMemberService.updateById(aladdinMember);
//        List<String> nodeUrlList = new ArrayList<>();
//        String endDate = DateUtil.format(serviceDo.getEndDate(), DatePattern.NORM_DATE_PATTERN);
//        List<NodeVmessDo> nodeVmessDoList = aladdinNodeVmessService.listByServiceId(serviceDo.getId(), year, month);
//        for (NodeVmessDo nodeVmessDo : nodeVmessDoList) {
//            if (nodeVmessDo.getNodeType().equals(Constant.NODE_TYPE_PRIVATE)) {
//                nodeUrlList.add(nodeVmessDo.convert());
//            }
//        }
//        NodeVmessDo nodeVmessDoForTime = new NodeVmessDo();
//        nodeVmessDoForTime.setNodePs("到期:" + endDate);
//        nodeVmessDoForTime.setNodeAdd("google.com");
//        nodeVmessDoForTime.setNodePort("443");
//        nodeVmessDoForTime.setNodeId("00000000-0000-0000-0000-000000000000");
//        nodeVmessDoForTime.setAid("0");
//        nodeVmessDoForTime.setNet("tcp");
//        nodeVmessDoForTime.setType("none");
//        nodeVmessDoForTime.setTls("none");
//        nodeUrlList.add(nodeVmessDoForTime.convert());
//        NodeVmessDo nodeVmessDoForSurplus = new NodeVmessDo();
//        nodeVmessDoForSurplus.setNodePs("本月剩余:" + serviceDo.getSurplus() + "GB" + (serviceDo.getSurplus().contains("-") ? "-已超额" : ""));
//        nodeVmessDoForSurplus.setNodeAdd("google.com");
//        nodeVmessDoForSurplus.setNodePort("443");
//        nodeVmessDoForSurplus.setNodeId("00000000-0000-0000-0000-000000000000");
//        nodeVmessDoForSurplus.setAid("0");
//        nodeVmessDoForSurplus.setNet("tcp");
//        nodeVmessDoForSurplus.setType("none");
//        nodeVmessDoForSurplus.setTls("none");
//        nodeUrlList.add(nodeVmessDoForSurplus.convert());
//        NodeVmessDo nodeVmessDoForWebsite = new NodeVmessDo();
//        nodeVmessDoForWebsite.setNodePs("官网: aladdinslamp.cc");
//        nodeVmessDoForWebsite.setNodeAdd("google.com");
//        nodeVmessDoForWebsite.setNodePort("443");
//        nodeVmessDoForWebsite.setNodeId("00000000-0000-0000-0000-000000000000");
//        nodeVmessDoForWebsite.setAid("0");
//        nodeVmessDoForWebsite.setNet("tcp");
//        nodeVmessDoForWebsite.setType("none");
//        nodeVmessDoForWebsite.setTls("none");
//        nodeUrlList.add(nodeVmessDoForWebsite.convert());
//        return nodeUrlList;
        return null;
    }
}
