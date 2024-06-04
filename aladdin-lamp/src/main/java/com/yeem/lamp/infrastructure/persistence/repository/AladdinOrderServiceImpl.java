package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.im.dto.SysTelegramSendDTO;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.infrastructure.persistence.service.impl.XUIService;
import com.yeem.lamp.security.Constant;
import com.yeem.lamp.infrastructure.persistence.entity.MemberDo;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinOrder;
import com.yeem.lamp.infrastructure.persistence.entity.PackageDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.AladdinOrderMapper;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinMemberService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinOrderService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinPackageService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Slf4j
@Service
public class AladdinOrderServiceImpl extends ServiceImpl<AladdinOrderMapper, AladdinOrder> implements IAladdinOrderService {

    @Autowired
    private AladdinOrderMapper aladdinOrderMapper;
    @Autowired
    private IAladdinPackageService aladdinPackageService;
    @Autowired
    private IAladdinServiceService aladdinServiceService;
    @Autowired
    private IAladdinMemberService aladdinMemberService;
    @Autowired
    private ISysTelegramService sysTelegramService;
    @Autowired
    private XUIService xuiService;
    @Autowired
    private Environment environment;

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<AladdinOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
    }

    @Override
    public IPage<AladdinOrder> pages(int current, int size) {
        QueryWrapper<AladdinOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<AladdinOrder> page = new Page<>(current, size);
        return aladdinOrderMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<AladdinOrder> listByMemberId(Long memberId) {
        QueryWrapper<AladdinOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.orderByDesc(BaseEntity.BaseField.CREATE_TIME.getName());
        return super.list(queryWrapper);
    }

    @Override
    public void place(AladdinOrder aladdinOrder) {
        PackageDo packageDo = aladdinPackageService.getById(aladdinOrder.getPackageId());
        String orderNo = "No" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + aladdinOrder.getMemberId();
        aladdinOrder.setOrderNo(orderNo);
        aladdinOrder.setOrderTime(new Date());
        aladdinOrder.setStatus("-1");
        aladdinOrder.setDataTraffic(packageDo.getDataTraffic());
        aladdinOrder.setPeriod(packageDo.getPeriod());
        aladdinOrder.setPrice(packageDo.getPrice());
        super.save(aladdinOrder);
    }

    @Override
    @Transactional
    public String pay(AladdinOrder aladdinOrder) {
        aladdinOrder = aladdinOrderMapper.selectById(aladdinOrder.getId());
        String merchantApi = environment.getProperty("merchant.api");
        if (StrUtil.isEmpty(merchantApi)) {
            log.error("未获取到商户API");
            return null;
        }
        String merchantId = environment.getProperty("merchant.id");
        if (StrUtil.isEmpty(merchantId)) {
            log.error("未获取到商户ID");
            return null;
        }
        String merchantKey = environment.getProperty("merchant.key");
        String notifyUrl = environment.getProperty("merchant.notify-url");
        String returnUrl = environment.getProperty("merchant.return-url");
        String sign = "clientip=" + "aladdinslamp.cc"
                + "&device=" + "pc"
                + "&money=" + aladdinOrder.getPrice()
                + "&name=" + "VIP会员"
                + "&notify_url=" + notifyUrl
                + "&out_trade_no=" + aladdinOrder.getOrderNo()
                + "&pid=" + merchantId
                + "&return_url=" + returnUrl
                + "&type=" + "alipay" + merchantKey;
        String md5Hex = MD5.create().digestHex(sign);
        Map<String, String> form = new HashMap<>();
        HttpResponse response = HttpRequest.post(merchantApi)
                .form("pid", merchantId)
                .form("type", "alipay")
                .form("out_trade_no", aladdinOrder.getOrderNo())
                .form("notify_url", notifyUrl)
                .form("return_url", returnUrl)
                .form("name", "VIP会员")
                .form("money", aladdinOrder.getPrice())
                .form("clientip", "aladdinslamp.cc")
                .form("device", "pc")
                .form("sign", md5Hex)
                .form("sign_type", "MD5")
                .execute();
        log.info("{}", response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            if ("1".equals(jsonNode.get("code").toString())) {
                aladdinOrder.setTradeNo(jsonNode.get("trade_no").toString().replace("\"", ""));
                aladdinOrderMapper.updateById(aladdinOrder);
            }
        } catch (IOException e) {
            log.error("update order info error");
        }
        return response.body();
    }

    @Override
    @Transactional
    public void finish(AladdinOrder aladdinOrder) {
        QueryWrapper<AladdinOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", aladdinOrder.getOrderNo());
        queryWrapper.eq("trade_no", aladdinOrder.getTradeNo());
        aladdinOrder = aladdinOrderMapper.selectOne(queryWrapper);
        try {
            MemberDo aladdinMember = aladdinMemberService.getById(aladdinOrder.getMemberId());
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("purchase");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            // 用户：#{email}购买了【时长：#{period}】-【流量：#{dataTraffic}】的【#{price}】元套餐，请注意Crisp客服消息！
            replaceMap.put("email", aladdinMember.getEmail());
            replaceMap.put("period", aladdinOrder.getPeriod());
            replaceMap.put("dataTraffic", aladdinOrder.getDataTraffic());
            replaceMap.put("price", aladdinOrder.getPrice());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
        if ("1".equals(aladdinOrder.getStatus())) {
            log.info("finish order：{}", aladdinOrder.getId());
            return;
        }
        List<ServiceDo> serviceDoList = aladdinServiceService.listByMemberId(aladdinOrder.getMemberId());
        Long serviceId = null;
        for (ServiceDo serviceDo : serviceDoList) {
            if (aladdinOrder.getDataTraffic().equals(serviceDo.getDataTraffic())) {
                serviceId = serviceDo.getId();
                if (serviceDo.getEndDate().before(new Date())) {
                    serviceDo.setEndDate(DateUtil.offsetMonth(new Date(), Integer.parseInt(aladdinOrder.getPeriod())));
                } else {
                    serviceDo.setEndDate(DateUtil.offsetMonth(serviceDo.getEndDate(), Integer.parseInt(aladdinOrder.getPeriod())));
                }
                serviceDo.setPeriod(aladdinOrder.getPeriod());
                serviceDo.setPrice(aladdinOrder.getPrice());
                aladdinServiceService.updateById(serviceDo);
                break;
            }
        }
        if (null == serviceId) {
            ServiceDo serviceDo = new ServiceDo();
            serviceDo.setMemberId(aladdinOrder.getMemberId());
            serviceDo.setBeginDate(new Date());
            serviceDo.setEndDate(DateUtil.offsetMonth(new Date(), Integer.parseInt(aladdinOrder.getPeriod())).toJdkDate());
            serviceDo.setDataTraffic(aladdinOrder.getDataTraffic());
            serviceDo.setPeriod(aladdinOrder.getPeriod());
            serviceDo.setPrice(aladdinOrder.getPrice());
            serviceDo.setUuid(UUID.fastUUID().toString());
            serviceDo.setStatus("0");
            aladdinServiceService.save(serviceDo);
            serviceId = serviceDo.getId();
        }
        // 更新状态
        aladdinOrder.setServiceId(serviceId);
        aladdinOrder.setCompleteTime(new Date());
        aladdinOrder.setStatus("1");
        log.info("service id:{}", aladdinOrder.getId());
        aladdinOrderMapper.updateById(aladdinOrder);
        xuiService.sync();
    }
}
