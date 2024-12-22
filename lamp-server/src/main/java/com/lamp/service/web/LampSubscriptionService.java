package com.lamp.service.web;

import cn.hutool.core.codec.Base64;import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampSubscription;
import com.lamp.mapper.LampSubscriptionMapper;
import com.lamp.xui.subscription.NodeVmess;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LampSubscriptionService extends ServiceImpl<LampSubscriptionMapper, LampSubscription> {

    @Autowired
    private LampSubscriptionMapper subscriptionMapper;

    @Autowired
    private LampMemberService memberService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public List<LampSubscription> list() {
        LambdaQueryWrapper<LampSubscription> queryWrapper = new LambdaQueryWrapper<>(LampSubscription.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return super.list(queryWrapper);
    }

    public String clash(String uuid) {
        LampMember member = memberService.getByUUID(uuid);
        if (member == null) {
            return null;
        }
        member.setLastSyncTime(LocalDateTime.now());
        memberService.updateById(member);
        List<LampSubscription> subscriptionList = list();
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        nodeVmessList.addAll(NodeVmess.generateVmessNode(subscriptionList, member.getUuid()));
        nodeVmessList.addAll(NodeVmess.generateSubscriptionVmessNode(member));

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
        LampMember member = memberService.getByUUID(uuid);
        if (member == null) {
            return null;
        }
        member.setLastSyncTime(LocalDateTime.now());
        memberService.updateById(member);
        List<LampSubscription> subscriptionList = list();
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        nodeVmessList.addAll(NodeVmess.generateVmessNode(subscriptionList, member.getUuid()));
        nodeVmessList.addAll(NodeVmess.generateSubscriptionVmessNode(member));

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
