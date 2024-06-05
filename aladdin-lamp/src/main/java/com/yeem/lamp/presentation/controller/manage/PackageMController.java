package com.yeem.lamp.presentation.controller.manage;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.application.dto.PackageDTO;
import com.yeem.lamp.application.service.PackageAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/package")
public class PackageMController {

    @Autowired
    private PackageAppService packageAppService;

    /**
     * 列表查询
     *
     * @return 列表信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(packageAppService.list());
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询列表失败");
        }
    }

    /**
     * 分页查询
     *
     * @param current 页码
     * @param size    页容量
     * @return 分页信息
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current,
                                        @RequestParam("size") int size) {
        try {
            return ResponseEntity.ok(packageAppService.pages(current, size));
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
            return ResponseEntity.ok(packageAppService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 更改
     *
     * @param packageDTO 套餐
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody PackageDTO packageDTO) {
        try {
            packageAppService.updateById(packageDTO);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更新失败");
        }
    }

    /**
     * 新增
     *
     * @param packageDTO aladdinPackage
     * @return 新增结果
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody PackageDTO packageDTO) {
        try {
            packageAppService.insert(packageDTO);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("新增失败");
        }
    }

    /**
     * 删除
     *
     * @param packageDTO 套餐
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody PackageDTO packageDTO) {
        try {
            packageAppService.deleteById(packageDTO.getId());
            return ResponseEntity.ok("删除");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
