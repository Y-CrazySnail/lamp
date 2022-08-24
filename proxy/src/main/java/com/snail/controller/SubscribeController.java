package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.entity.Member;
import com.snail.entity.Node;
import com.snail.entity.Server;
import com.snail.service.IMemberService;
import com.snail.service.INodeService;
import com.snail.service.IServerService;
import com.snail.utils.FreeMakerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private INodeService nodeService;
    @Autowired
    private IServerService serverService;

    /**
     * Mac订阅
     */
    @GetMapping("/mac/{uuid}")
    public String mac(@PathVariable("uuid") String uuid) {
        try {
            return generateSubscribe(uuid);
        } catch (Exception e) {
            log.info("更新订阅异常：", e);
            return null;
        }
    }

    /**
     * Andriod订阅
     */
    @GetMapping("/android/{uuid}")
    public String android(@PathVariable("uuid") String uuid) {
        try {
            String subscribe = generateSubscribe(uuid);
            return Base64.encodeBase64String(subscribe.getBytes());
        } catch (Exception e) {
            log.info("更新订阅异常：", e);
            return null;
        }
    }

    /**
     * Windows订阅
     */
    @GetMapping("/windows/{uuid}")
    public String windows(@PathVariable("uuid") String uuid) {
        try {
            String subscribe = generateSubscribe(uuid);
            return Base64.encodeBase64String(subscribe.getBytes());
        } catch (Exception e) {
            log.info("更新订阅异常：", e);
            return null;
        }
    }

    /**
     * IOS订阅
     */
    @GetMapping("/ios/{uuid}")
    public String ios(@PathVariable("uuid") String uuid) {
        try {
            String subscribe = generateSubscribe(uuid);
            return Base64.encodeBase64String(subscribe.getBytes());
        } catch (Exception e) {
            log.info("更新订阅异常：", e);
            return null;
        }
    }

    private String generateSubscribe(String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("wechat", Base64.decodeBase64(uuid.getBytes())).or().eq("qq", Base64.decodeBase64(uuid.getBytes()));
        Member member = memberService.getOne(memberQueryWrapper);
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq("member_id", member.getId());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        List<Server> serverList = serverService.list();
        StringBuilder subscribe = new StringBuilder();
        serverList.forEach(server -> {
            Map<String, Object> replaceMap = new HashMap<>();
            replaceMap.put("uuid", nodeList.get(0).getUuid());
            replaceMap.put("xray_domain", server.getXrayDomain());
            replaceMap.put("xray_port", server.getXrayPort());
            replaceMap.put("xray_ws_path", server.getXrayWsPath());
            replaceMap.put("region", server.getRegion());
            replaceMap.put("traffic", BigDecimal.valueOf((float) member.getTrafficSurplusMonth() / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP).doubleValue());
            subscribe.append(FreeMakerUtils.getContent("/usr/snail/config/template/", "xray_subscribe.ftl", replaceMap));
            subscribe.append("\n");
        });
        return subscribe.toString();
    }
}
