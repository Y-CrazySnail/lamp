package com.lamp.controller.manage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.entity.LampMember;
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

import static org.mockito.Mockito.*;
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
        MvcResult result = mockMvc.perform(get("/manage/lamp-member/get?id=1"))
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
        member.setReferrerCode("OOOO");
        member.setReferralCode("DDDD");
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
        member.setId(2L);
        member.setWechat("wechat11");
        member.setEmail("email1@example.com1");
        member.setPassword("password11");
        member.setLevel(1);
        member.setReferrerCode("OOOO1");
        member.setReferralCode("DDDD1");
        member.setRemark("remark11");
        member.setBalance(new BigDecimal("1001.00"));

        mockMvc.perform(put("/manage/lamp-member/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
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
