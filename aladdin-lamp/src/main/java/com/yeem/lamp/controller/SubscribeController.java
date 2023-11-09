package com.yeem.lamp.controller;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.lamp.entity.Member;
import com.yeem.lamp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private IAladdinNodeService aladdinNodeService;

    @Autowired
    private IMemberService memberService;

    @GetMapping("/clash/{uuid}")
    public String clash(@PathVariable("uuid") String uuid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("node_id", uuid);
        memberQueryWrapper.ge("end", new Date());
        Member member = memberService.getOne(memberQueryWrapper);
        if (StringUtils.isEmpty(member)) {
            return "";
        }
        try {
            List<String> nodeUrlList = aladdinNodeService.getNodeUrlList(uuid);
            String url = String.join("|", nodeUrlList);
            log.info("URL:{}", url);
            String sub = "http://127.0.0.1:25500/sub?target=clash&url=" + URLEncodeUtil.encode(url);
            return HttpUtil.createRequest(Method.GET, sub).execute().body();
        } catch (Exception e) {
            log.info("更新订阅异常：{}", uuid, e);
            return null;
        }
    }

    @GetMapping("/shadowrocket/{uuid}")
    public String shadowrocket(@PathVariable("uuid") String uuid) {
        log.info("uuid:{}", uuid);
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("node_id", uuid);
        memberQueryWrapper.ge("end", new Date());
        Member member = memberService.getOne(memberQueryWrapper);
        if (StringUtils.isEmpty(member)) {
            return "已过期";
        }
        try {
            List<String> nodeUrlList = aladdinNodeService.getNodeUrlList(uuid);
            String url = String.join("|", nodeUrlList);
            log.info("URL:{}", url);
            String sub = "http://127.0.0.1:25500/sub?target=v2ray&url=" + URLEncodeUtil.encode(url);
            return HttpUtil.createRequest(Method.GET, sub).execute().body();
        } catch (Exception e) {
            log.info("更新订阅异常：{}", uuid, e);
            return null;
        }
    }
}
