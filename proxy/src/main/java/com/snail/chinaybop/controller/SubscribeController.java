package com.snail.chinaybop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.chinaybop.entity.Member;
import com.snail.chinaybop.entity.Node;
import com.snail.chinaybop.service.IMemberService;
import com.snail.chinaybop.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private INodeService nodeService;

    /**
     * Mac订阅
     */
    @GetMapping("/mac/{uuid}")
    public String mac(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("wechat", Base64.decodeBase64(uuid.getBytes())).or().eq("qq", Base64.decodeBase64(uuid.getBytes()));
        Member member = memberService.getOne(memberQueryWrapper);
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq("member_id", member.getId());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        if (nodeList.size() == 0) {
            return "已过期，请联系管理员";
        }
        return "vless://" + nodeList.get(0).getUuid() + "@" + nodeList.get(0).getDomain() + ":" + nodeList.get(0).getPort() + "?type=ws&security=tls&path=%2Fc5fa7e2466516a1%2F&sni=" + nodeList.get(0).getDomain() + "#USA_" + BigDecimal.valueOf((float) member.getTrafficSurplusMonth() / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP).doubleValue() + "GB";
    }

    /**
     * Andriod订阅
     */
    @GetMapping("/android/{uuid}")
    public String android(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("wechat", Base64.decodeBase64(uuid.getBytes())).or().eq("qq", Base64.decodeBase64(uuid.getBytes()));
        Member member = memberService.getOne(memberQueryWrapper);
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq("member_id", member.getId());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        if (nodeList.size() == 0) {
            return "已过期，请联系管理员";
        }
        return Base64.encodeBase64String(("vless://" + nodeList.get(0).getUuid() + "@" + nodeList.get(0).getDomain() + ":" + nodeList.get(0).getPort() + "?type=ws&security=tls&path=%2Fc5fa7e2466516a1%2F&sni=" + nodeList.get(0).getDomain() + "#USA_" + BigDecimal.valueOf((float) member.getTrafficSurplusMonth() / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP).doubleValue() + "GB").getBytes());
    }

    /**
     * Windows订阅
     */
    @GetMapping("/windows/{uuid}")
    public String windows(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("wechat", Base64.decodeBase64(uuid.getBytes())).or().eq("qq", Base64.decodeBase64(uuid.getBytes()));
        Member member = memberService.getOne(memberQueryWrapper);
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq("member_id", member.getId());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        if (nodeList.size() == 0) {
            return "已过期，请联系管理员";
        }
        return Base64.encodeBase64String(("vless://" + nodeList.get(0).getUuid() + "@" + nodeList.get(0).getDomain() + ":" + nodeList.get(0).getPort() + "?type=ws&security=tls&path=%2Fc5fa7e2466516a1%2F&sni=" + nodeList.get(0).getDomain() + "#USA_" + BigDecimal.valueOf((float) member.getTrafficSurplusMonth() / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP).doubleValue() + "GB").getBytes());
    }

    /**
     * IOS订阅
     */
    @GetMapping("/ios/{uuid}")
    public String ios(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("wechat", Base64.decodeBase64(uuid.getBytes())).or().eq("qq", Base64.decodeBase64(uuid.getBytes()));
        Member member = memberService.getOne(memberQueryWrapper);
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq("member_id", member.getId());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        if (nodeList.size() == 0) {
            return "已过期，请联系管理员";
        }
        return Base64.encodeBase64String(("vless://" + nodeList.get(0).getUuid() + "@" + nodeList.get(0).getDomain() + ":" + nodeList.get(0).getPort() + "?type=ws&security=tls&path=%2Fc5fa7e2466516a1%2F&sni=" + nodeList.get(0).getDomain() + "#USA_" + BigDecimal.valueOf((float) member.getTrafficSurplusMonth() / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP).doubleValue() + "GB").getBytes());
    }
}
