package com.yeem.car.controller.manager;

import cn.hutool.http.HttpStatus;
import com.yeem.car.entity.BaseCarBrand;
import com.yeem.car.service.ICarBrandService;
import com.yeem.car.log.OperateLog;
import com.yeem.common.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/base-car-brand")
public class BaseCarBrandController extends BaseController<BaseCarBrand> {
    @Autowired
    private ICarBrandService carBrandService;

    /**
     * 查询所有
     *
     * @return
     */
    @OperateLog(operateModule = "Brand模块", operateType = "list查询", operateDesc = "描述:查询Brand全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(carBrandService.list());
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    /**
     * 分页模糊查询
     *
     * @param current
     * @param size
     * @param name
     * @return
     */
    @OperateLog(operateModule = "Brand模块", operateType = "pages查询", operateDesc = "描述:分页查询Brand全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current,
                                        @RequestParam("size") int size,
                                        @RequestParam(value = "name", required = false) String name) {
        try {
            return ResponseEntity.ok(carBrandService.pages(current, size, name));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页模糊查询失败");
        }
    }

    /**
     * id查单个
     *
     * @param id
     * @return
     */
    @OperateLog(operateModule = "Brand模块", operateType = "getById查询", operateDesc = "描述:ID查询Brand全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (null == id) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询 id为空");
            }
            return ResponseEntity.ok(carBrandService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 删除
     *
     * @param baseCarBrand
     * @return
     */
    @OperateLog(operateModule = "Brand模块", operateType = "delete", operateDesc = "描述:软删除Brand全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody BaseCarBrand baseCarBrand) {
        try {
            carBrandService.remove(baseCarBrand);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("软删除失败");
        }
    }

    /**
     * 更改
     *
     * @param baseCarBrand
     * @return
     */
    @OperateLog(operateModule = "Brand模块", operateType = "update", operateDesc = "描述:修改Brand全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody BaseCarBrand baseCarBrand) {
        try {
            if (baseCarBrand.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("品牌名为空");
            }
            if (baseCarBrand.getLogoPath().isEmpty()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("log文件路径为空");
            }
            carBrandService.update(baseCarBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("修改失败");
        }
    }

    /**
     * 新增
     *
     * @param baseCarBrand
     * @return
     */
    @OperateLog(operateModule = "Brand模块", operateType = "save", operateDesc = "描述:保存Brand全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody BaseCarBrand baseCarBrand) {
        try {
            if (baseCarBrand.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("品牌名为空");
            }
            if (baseCarBrand.getLogoPath().isEmpty()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("log文件路径为空");
            }
            carBrandService.save(baseCarBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("add失败");
        }
    }
}
