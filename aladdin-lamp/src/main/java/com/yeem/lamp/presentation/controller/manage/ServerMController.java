package com.yeem.lamp.presentation.controller.manage;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.application.dto.ServerDTO;
import com.yeem.lamp.application.service.manage.ServerManageAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/server")
public class ServerMController {

    @Autowired
    private ServerManageAppService serverAppService;

    /**
     * 服务器-列表查询
     *
     * @return 列表信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(serverAppService.list());
        } catch (Exception e) {
            log.error("服务器-列表查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("服务器-列表查询失败");
        }
    }

    /**
     * 服务器-分页查询
     *
     * @param current 页码
     * @param size    页容量
     * @return 分页信息
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current,
                                        @RequestParam("size") int size) {
        try {
            return ResponseEntity.ok(serverAppService.pages(current, size));
        } catch (Exception e) {
            log.error("服务器-分页查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("服务器-分页查询失败");
        }
    }

    /**
     * 服务器-根据ID查询
     *
     * @param id ID
     * @return 服务器信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (null == id) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("服务器-根据ID查询查询失败：ID为空");
            }
            return ResponseEntity.ok(serverAppService.getById(id));
        } catch (Exception e) {
            log.error("服务器-根据ID查询查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("服务器-根据ID查询查询失败");
        }
    }

    /**
     * 服务器-新增
     *
     * @param serverDTO serverDTO
     * @return 新增结果
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody ServerDTO serverDTO) {
        try {
            serverAppService.save(serverDTO);
            return ResponseEntity.ok("服务器-新增成功");
        } catch (Exception e) {
            log.error("服务器-新增失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("服务器-新增失败");
        }
    }

    /**
     * 服务器-更新
     *
     * @param serverDTO serverDTO
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ServerDTO serverDTO) {
        try {
            serverAppService.updateById(serverDTO);
            return ResponseEntity.ok("服务器-更新成功");
        } catch (Exception e) {
            log.error("服务器-更新失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("服务器-更新失败");
        }
    }

    /**
     * 服务器-删除
     *
     * @param serverDTO serverDTO
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody ServerDTO serverDTO) {
        try {
            if (null == serverDTO.getId()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("服务器-删除失败：ID为空");
            }
            serverAppService.removeById(serverDTO.getId());
            return ResponseEntity.ok("服务器-删除成功");
        } catch (Exception e) {
            log.error("服务器-删除失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("服务器-删除失败");
        }
    }
}
