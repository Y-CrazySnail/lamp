package com.yeem.car.film.controller.web;

import cn.hutool.http.HttpStatus;
import com.yeem.car.film.entity.BaseCarBrand;
import com.yeem.car.film.entity.BaseCarModel;
import com.yeem.car.film.log.OperateLog;
import com.yeem.car.film.service.ICarModelService;
import com.yeem.common.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/web/base-car-model")
public class WebBaseCarModelController extends BaseController<BaseCarBrand> {
    @Autowired
    private ICarModelService ICarModelService;

    /**
     * 查询所有
     *
     * @return
     */
    @OperateLog(operateModule = "model模块", operateType = "list查询", operateDesc = "描述:查询model全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(ICarModelService.list());
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
     * @return
     */
    @OperateLog(operateModule = "model模块", operateType = "pages查询", operateDesc = "描述:分页查询model全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam(value = "name", required = false) String name) {
        try {
            return ResponseEntity.ok(ICarModelService.pages(current, size, name));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    /**
     * id查单个
     *
     * @param id
     * @return
     */
    @OperateLog(operateModule = "model模块", operateType = "getById查询", operateDesc = "描述:ID查询model全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (!ICarModelService.getById(id).getDeleteFlag()) {
                return ResponseEntity.ok(ICarModelService.getById(id));
            } else {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不可查询软删除用户");
            }
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 根据brand_id删除
     *
     * @param baseCarModel
     * @return
     */
    @OperateLog(operateModule = "model模块", operateType = "delete", operateDesc = "描述:软删除model全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> removeByBrandId(@RequestBody BaseCarModel baseCarModel) {
        try {
            ICarModelService.removeByBrandId(baseCarModel.getId());
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("软删除失败");
        }
    }

    /**
     * 更改
     *
     * @param baseCarModelList
     * @return
     */
    @OperateLog(operateModule = "model模块", operateType = "update", operateDesc = "描述:修改model全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody List<BaseCarModel> baseCarModelList) {
        try {
            ICarModelService.update(baseCarModelList);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("修改失败");
        }
    }

    /**
     * 增加
     *
     * @param baseCarModel
     * @return
     */
    @OperateLog(operateModule = "model模块", operateType = "save", operateDesc = "描述:保存model全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody BaseCarModel baseCarModel) {
        try {
            ICarModelService.save(baseCarModel);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("add失败");
        }
    }
}
