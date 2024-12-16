package com.lamp.presentation.controller.manage;

import cn.hutool.http.HttpStatus;
import com.lamp.application.dto.PackageDTO;
import com.lamp.application.service.manage.PackageManageAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/package")
public class PackageMController {

    @Autowired
    private PackageManageAppService packageAppService;

    /**
     * 套餐-列表查询
     *
     * @return 套餐-列表信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(packageAppService.list());
        } catch (Exception e) {
            log.error("套餐-列表查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("套餐-列表查询失败");
        }
    }

    /**
     * 套餐-分页查询
     *
     * @param current 页码
     * @param size    页容量
     * @return 分页信息
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> page(@RequestParam("current") int current,
                                       @RequestParam("size") int size) {
        try {
            return ResponseEntity.ok(packageAppService.page(current, size));
        } catch (Exception e) {
            log.error("套餐-分页查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("套餐-分页查询失败");
        }
    }

    /**
     * 套餐-根据ID查询
     *
     * @param id ID
     * @return 套餐信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (null == id) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("套餐-根据ID查询失败：ID为空");
            }
            return ResponseEntity.ok(packageAppService.getById(id));
        } catch (Exception e) {
            log.error("套餐-根据ID查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("套餐-根据ID查询失败");
        }
    }

    /**
     * 套餐-更新
     *
     * @param packageDTO 套餐
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody PackageDTO packageDTO) {
        try {
            packageAppService.updateById(packageDTO);
            return ResponseEntity.ok("套餐-更新成功");
        } catch (Exception e) {
            log.error("套餐-更新失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("套餐-更新失败");
        }
    }

    /**
     * 套餐-新增
     *
     * @param packageDTO aladdinPackage
     * @return 新增结果
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody PackageDTO packageDTO) {
        try {
            packageAppService.insert(packageDTO);
            return ResponseEntity.ok("套餐-新增成功");
        } catch (Exception e) {
            log.error("套餐-新增失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("套餐-新增失败");
        }
    }

    /**
     * 套餐-删除
     *
     * @param packageDTO 套餐
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody PackageDTO packageDTO) {
        try {
            packageAppService.deleteById(packageDTO.getId());
            return ResponseEntity.ok("套餐-删除成功");
        } catch (Exception e) {
            log.error("套餐-删除失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("套餐-删除失败");
        }
    }
}
