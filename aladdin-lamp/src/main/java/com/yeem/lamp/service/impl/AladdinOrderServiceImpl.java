package com.yeem.lamp.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinOrder;
import com.yeem.lamp.entity.AladdinPackage;
import com.yeem.lamp.mapper.AladdinOrderMapper;
import com.yeem.lamp.service.IAladdinOrderService;
import com.yeem.lamp.service.IAladdinPackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.digest.MD5;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AladdinOrderServiceImpl extends ServiceImpl<AladdinOrderMapper, AladdinOrder> implements IAladdinOrderService {

    @Autowired
    private AladdinOrderMapper aladdinOrderMapper;
    @Autowired
    private IAladdinPackageService aladdinPackageService;
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
        AladdinPackage aladdinPackage = aladdinPackageService.getById(aladdinOrder.getPackageId());
        String orderNo = "No" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + aladdinOrder.getMemberId();
        aladdinOrder.setOrderNo(orderNo);
        aladdinOrder.setOrderTime(new Date());
        aladdinOrder.setStatus("-1");
        aladdinOrder.setDataTraffic(aladdinPackage.getDataTraffic());
        aladdinOrder.setPeriod(aladdinPackage.getPeriod());
        aladdinOrder.setPrice(aladdinPackage.getPrice());
        super.save(aladdinOrder);
    }

    @Override
    public void pay(AladdinOrder aladdinOrder) {
        aladdinOrder.setOrderNo("test1234546678879");
        String merchantApi = environment.getProperty("merchant.api");
        Integer merchantId = Integer.valueOf(environment.getProperty("merchant.id"));
        String merchantKey = environment.getProperty("merchant.key");
        String sign = "clientip=" + "aladdinslamp.cc"
                + "&device=" + "pc"
                + "&money=" + "140.00"
                + "&name=" + "VIP会员"
                + "&notify_url=" + "http://aladdinslamp.cc/notify_url.php"
                + "&out_trade_no=" + aladdinOrder.getOrderNo()
                + "&pid=" + merchantId
                + "&return_url=" + "http://aladdinslamp.cc/return_url.php"
                + "&type=" + "alipay" + merchantKey;
        String md5Hex = MD5.create().digestHex(sign);
        Map<String, String> form = new HashMap<>();
        HttpResponse response = HttpRequest.post(merchantApi)
                .form("pid", merchantId)
                .form("type", "alipay")
                .form("out_trade_no", aladdinOrder.getOrderNo())
                .form("notify_url", "http://aladdinslamp.cc/notify_url.php")
                .form("return_url", "http://aladdinslamp.cc/return_url.php")
                .form("name", "VIP会员")
                .form("money", "140.00")
                .form("clientip", "aladdinslamp.cc")
                .form("device", "pc")
                .form("sign", md5Hex)
                .form("sign_type", "MD5")
                .execute();
        log.info(response.body());
    }

    @Override
    public void finish(AladdinOrder aladdinOrder) {
        // todo 更新订单信息
        // todo 创建Service
    }
}
