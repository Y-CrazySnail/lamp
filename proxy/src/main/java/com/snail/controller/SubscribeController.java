package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.entity.Member;
import com.snail.entity.Node;
import com.snail.mapper.MemberMapper;
import com.snail.service.IMemberService;
import com.snail.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private INodeService nodeService;
    @Autowired
    private MemberMapper memberMapper;

    /**
     * Mac订阅
     */
    @GetMapping("/mac/{uuid}")
    public String mac(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("uuid", uuid);
        Member member = memberService.getOne(memberQueryWrapper);
        if (!member.getMac().equals(1)) {
            return "异常，请联系管理员";
        }
        if (member.getMacNum() > 0) {
            return "异常，请联系管理员";
        }
        if (StringUtils.isEmpty(member)) {
            return "已过期，请联系管理员";
        }
        memberMapper.increaseMacNum(member.getId());

        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.le("start_time", member.getEnd());
        nodeQueryWrapper.ge("end_time", member.getEnd());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        if (nodeList.size() == 0) {
            return "已过期，请联系管理员";
        }

        return nodeList.stream().map(Node::getUrl).collect(Collectors.joining("\n"));
    }

    /**
     * Andriod订阅
     */
    @GetMapping("/android/{uuid}")
    public String andriod(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("uuid", uuid);
        Member member = memberService.getOne(memberQueryWrapper);
        if (!member.getAndroid().equals(1)) {
            return "异常，请联系管理员";
        }
        if (member.getAndroidNum() > 0) {
            return "异常，请联系管理员";
        }
        if (StringUtils.isEmpty(member)) {
            return "已过期，请联系管理员";
        }
        memberMapper.increaseAndroidNum(member.getId());

        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.le("start_time", member.getEnd());
        nodeQueryWrapper.ge("end_time", member.getEnd());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        if (nodeList.size() == 0) {
            return "已过期，请联系管理员";
        }

        return Base64.getEncoder().encodeToString(nodeList.stream().map(Node::getUrl).collect(Collectors.joining("\n")).getBytes());
    }

    /**
     * Windows订阅
     */
    @GetMapping("/windows/{uuid}")
    public String windows(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("uuid", uuid);
        Member member = memberService.getOne(memberQueryWrapper);
        if (!member.getWindows().equals(1)) {
            return "异常，请联系管理员";
        }
        if (member.getWindowsNum() > 0) {
            return "异常，请联系管理员";
        }
        if (StringUtils.isEmpty(member)) {
            return "已过期，请联系管理员";
        }
        memberMapper.increaseWindowsNum(member.getId());

        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.le("start_time", member.getEnd());
        nodeQueryWrapper.ge("end_time", member.getEnd());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        if (nodeList.size() == 0) {
            return "已过期，请联系管理员";
        }

        return Base64.getEncoder().encodeToString(nodeList.stream().map(Node::getUrl).collect(Collectors.joining("\n")).getBytes());
    }

    /**
     * IOS订阅
     */
    @GetMapping("/ios/{uuid}")
    public String ios(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("uuid", uuid);
        Member member = memberService.getOne(memberQueryWrapper);
        if (!member.getIphone().equals(1)) {
            return "异常，请联系管理员";
        }
        if (member.getIphoneNum() > 0) {
            return "异常，请联系管理员";
        }
        if (StringUtils.isEmpty(member)) {
            return "已过期，请联系管理员";
        }
        memberMapper.increaseIphoneNum(member.getId());

        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.le("start_time", member.getEnd());
        nodeQueryWrapper.ge("end_time", member.getEnd());
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        if (nodeList.size() == 0) {
            return "已过期，请联系管理员";
        }

        return Base64.getEncoder().encodeToString(nodeList.stream().map(Node::getUrl).collect(Collectors.joining("\n")).getBytes());
    }
}
