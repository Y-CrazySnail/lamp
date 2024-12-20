package com.lamp.controller.manage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampService;
import com.lamp.entity.LampServiceMonth;
import com.lamp.service.manage.MLampMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MLampMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/manage/lamp-member/page?page.size=15&page.current=1"))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get("/manage/lamp-member/get?id=3"))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    void testSave() throws Exception {
        LampMember member = new LampMember();
        member.setWechat("wechat1");
        member.setEmail("email1@example.com");
        member.setPassword("password1");
        member.setLevel(1);
        member.setReferrerCode("OOOO0");
        member.setReferralCode("DDDDD");
        member.setRemark("remark1");
        member.setBalance(new BigDecimal("100.00"));

        mockMvc.perform(post("/manage/lamp-member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isOk())
                .andExpect(content().string("会员添加成功"));
    }

    @Test
    void testUpdate() throws Exception {
        LampMember member = new LampMember();
        member.setId(3L);
        member.setWechat("wechat11");
        member.setEmail("email1@example.com1");
        member.setPassword("password11");
        member.setLevel(1);
        member.setReferrerCode("OOOO1");
        member.setReferralCode("DDDD1");
        member.setRemark("remark11");
        member.setBalance(new BigDecimal("1001.00"));

        LampService service = new LampService();
        service.setId(2L);
        service.setUuid("4460efbb-ef23-4ffc-b4fc-9c39cac51c0c");
//        service.setExpiryDate(LocalDateTimeUtil.offset(LocalDateTime.now(), 1, ChronoUnit.MONTHS));
        service.setBandwidth(0L);
        service.setPeriod(1);

        LampServiceMonth serviceMonth = new LampServiceMonth();
        serviceMonth.setId(2L);
        serviceMonth.setBandwidth(20000L);
        serviceMonth.setBandwidth(1L);
        serviceMonth.setBandwidthDown(5L);

        service.setServiceMonthList(Collections.singletonList(serviceMonth));

        member.setServiceList(Collections.singletonList(service));
        mockMvc.perform(put("/manage/lamp-member/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":3,\"createUser\":\"anonymousUser\",\"createTime\":null,\"updateUser\":\"anonymousUser\",\"updateTime\":null,\"deleteFlag\":false,\"idList\":null,\"page\":null,\"wechat\":\"wechat1\",\"email\":\"email1@example.com\",\"username\":null,\"password\":\"password12\",\"level\":1,\"referrerCode\":\"OOOO02\",\"referralCode\":\"DDDDD2\",\"remark\":\"remark1\",\"balance\":100.100,\"token\":null,\"serviceList\":[{\"id\":2,\"createUser\":\"anonymousUser\",\"createTime\":null,\"updateUser\":\"anonymousUser\",\"updateTime\":null,\"deleteFlag\":false,\"idList\":null,\"page\":null,\"memberId\":3,\"uuid\":\"4460efbb-ef23-4ffc-b4fc-9c39cac51c0c\",\"endDate\":\"2024-12-18T16:00:00.000+0000\",\"bandwidth\":1,\"period\":1,\"lastSyncTime\":null,\"serviceMonthList\":[{\"id\":2,\"createUser\":\"anonymousUser\",\"createTime\":null,\"updateUser\":\"anonymousUser\",\"updateTime\":null,\"deleteFlag\":false,\"idList\":null,\"page\":null,\"serviceId\":2,\"serviceYear\":2024,\"serviceMonth\":12,\"bandwidth\":1,\"bandwidthUp\":11,\"bandwidthDown\":2,\"clientTrafficList\":[]}]}]}"))
                .andExpect(status().isOk())
                .andExpect(content().string("会员信息更新成功"));
    }

    @Test
    void testRemove() throws Exception {
        LampMember member = new LampMember();
        member.setId(2L);
        member.setWechat("wechat11");
        member.setEmail("email1@example.com1");
        member.setPassword("password11");
        member.setLevel(1);
        member.setReferrerCode("OOOO1");
        member.setReferralCode("DDDD1");
        member.setRemark("remark11");
        member.setBalance(new BigDecimal("1001.00"));

        mockMvc.perform(delete("/manage/lamp-member/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isOk())
                .andExpect(content().string("会员删除成功"));
    }
}
