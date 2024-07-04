package com.yeem.lamp.presentation.controller.manage;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.application.service.ServiceAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/service")
public class ServiceMController {

    @Autowired
    private ServiceAppService serviceAppService;

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
            return ResponseEntity.ok(serviceAppService.pageService(current, size, memberId, status, wechat, email));
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
            return ResponseEntity.ok(serviceAppService.getServiceById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 根据会员ID查询
     *
     * @param memberId 会员ID
     * @return 服务信息
     */
    @GetMapping("/getByMemberId")
    public ResponseEntity<Object> getByMemberId(@RequestParam("memberId") Long memberId) {
        try {
            if (null == memberId) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按会员id查询 id为空");
            }
            return ResponseEntity.ok(serviceAppService.listServiceByMemberId(memberId));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 更改
     *
     * @param serviceDTO serviceDTO
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ServiceDTO serviceDTO) {
        try {
            serviceAppService.updateServiceById(serviceDTO);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更新失败");
        }
    }

    /**
     * 新增
     *
     * @param serviceDTO aladdinService
     * @return 新增结果
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody ServiceDTO serviceDTO) {
        try {
            serviceAppService.saveService(serviceDTO);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("新增失败");
        }
    }

    /**
     * 删除
     *
     * @param serviceDTO serviceDTO
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody ServiceDTO serviceDTO) {
        try {
            if (null == serviceDTO.getId()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
            }
            serviceAppService.removeServiceById(serviceDTO.getId());
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
