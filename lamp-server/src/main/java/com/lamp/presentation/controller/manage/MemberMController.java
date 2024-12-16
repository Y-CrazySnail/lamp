package com.lamp.presentation.controller.manage;

import cn.hutool.http.HttpStatus;
import com.lamp.application.dto.MemberDTO;
import com.lamp.application.service.manage.MemberManageAppService;
import com.lamp.infrastructure.persistence.entity.MemberDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/member")
public class MemberMController {

    @Autowired
    private MemberManageAppService memberAppService;

    /**
     * 会员-列表查询
     *
     * @return 列表信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(memberAppService.list(new MemberDTO()));
        } catch (Exception e) {
            log.error("会员-列表查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("会员-列表查询失败");
        }
    }

    /**
     * 会员-分页查询
     *
     * @param current 页码
     * @param size    页容量
     * @param email   邮箱
     * @param wechat  微信
     * @return 分页信息
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> page(@RequestParam("current") int current,
                                       @RequestParam("size") int size,
                                       @RequestParam(value = "email", required = false) String email,
                                       @RequestParam(value = "wechat", required = false) String wechat) {
        try {
            return ResponseEntity.ok(memberAppService.pages(current, size, email, wechat));
        } catch (Exception e) {
            log.error("会员-分页查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("会员-分页查询失败");
        }
    }

    /**
     * 会员-根据ID查询
     *
     * @param id ID
     * @return 会员信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (null == id) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("会员-根据ID查询失败：ID为空");
            }
            return ResponseEntity.ok(memberAppService.getById(id));
        } catch (Exception e) {
            log.error("会员-根据ID查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("会员-根据ID查询失败");
        }
    }

    /**
     * 会员-更新
     *
     * @param memberDTO 会员信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody MemberDTO memberDTO) {
        try {
            memberAppService.updateById(memberDTO);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("会员-更新失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("会员-更新失败");
        }
    }

    /**
     * 会员-新增
     *
     * @param memberDTO 会员信息
     * @return 新增结果
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody MemberDTO memberDTO) {
        try {
            memberAppService.save(memberDTO);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("会员-新增失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("会员-新增失败");
        }
    }

    /**
     * 会员-删除
     *
     * @param memberDo 会员信息
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody MemberDo memberDo) {
        try {
            if (null == memberDo.getId()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("会员-删除失败");
            }
            memberAppService.removeById(memberDo.getId());
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("会员-删除失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("会员-删除失败");
        }
    }
}
