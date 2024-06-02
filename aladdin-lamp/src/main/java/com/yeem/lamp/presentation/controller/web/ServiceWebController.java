package com.yeem.lamp.presentation.controller.web;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.presentation.interceptor.LocalAuthInterceptor;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/service")
public class ServiceWebController {

    @Autowired
    private IAladdinServiceService aladdinServiceService;

    @Autowired
    private ServiceAppService aladdinServiceAppService;
    /**
     * 列表查询
     *
     * @return 会员信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(aladdinServiceService.listByMemberId(memberId));
        } catch (Exception e) {
            log.error("list查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("list查询失败");
        }
    }

    /**
     * 更改
     *
     * @param serviceDo AladdinService
     * @return 更新结果
     */
    @PutMapping("/updateUUID")
    public ResponseEntity<Object> updateUUID(@RequestBody ServiceDo serviceDo) {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            UpdateWrapper<ServiceDo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("uuid", serviceDo.getUuid());
            updateWrapper.set("status", "0");
            updateWrapper.eq(BaseEntity.BaseField.ID.getName(), serviceDo.getId());
            updateWrapper.eq("member_id", memberId);
            aladdinServiceService.update(updateWrapper);
            return ResponseEntity.ok("更换UUID成功");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更新失败");
        }
    }
}
