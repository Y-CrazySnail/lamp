package com.yeem.lamp.presentation.controller.manage;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/service")
public class MAladdinServiceController {
    @Autowired
    private IAladdinServiceService aladdinServiceService;
    @Autowired
    private ServiceAppService aladdinServiceAppService;
    /**
     * 分页查询
     *
     * @param current  页码
     * @param size     页容量
     * @param memberId 会员ID
     * @param status   状态 0异常 1正常
     * @return 分页信息
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current,
                                        @RequestParam("size") int size,
                                        @RequestParam(value = "memberId", required = false) Long memberId,
                                        @RequestParam(value = "status", required = false) String status,
                                        @RequestParam(value = "wechat", required = false) String wechat,
                                        @RequestParam(value = "email", required = false) String email) {
        try {
            return ResponseEntity.ok(aladdinServiceService.pages(current, size, memberId, status, wechat, email));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询失败");
        }
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 会员信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (null == id) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询 id为空");
            }
            return ResponseEntity.ok(aladdinServiceService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 更改
     *
     * @param aladdinService aladdinService
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AladdinService aladdinService) {
        try {
            aladdinServiceService.updateById(aladdinService);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更新失败");
        }
    }

    /**
     * 新增
     *
     * @param aladdinService aladdinService
     * @return 新增结果
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody AladdinService aladdinService) {
        try {
            aladdinServiceService.save(aladdinService);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("新增失败");
        }
    }

    /**
     * 删除
     *
     * @param aladdinService aladdinService
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody AladdinService aladdinService) {
        try {
            if (null == aladdinService.getId()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
            }
            aladdinServiceService.removeById(aladdinService.getId());
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
