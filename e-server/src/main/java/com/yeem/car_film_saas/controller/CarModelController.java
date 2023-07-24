package com.yeem.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.yeem.car_film_saas.entity.CarBrand;
import com.yeem.car_film_saas.entity.CarModel;
import com.yeem.car_film_saas.service.ICarModelService;
import com.yeem.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-model")
public class CarModelController extends BaseController<CarBrand> {
    @Autowired
    private ICarModelService ICarModelService;

    /**
     * 查询所有
     *
     * @return
     */
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
    @GetMapping("/pages")
    public ResponseEntity<Object> page(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam(value = "name", required = false) String name) {
        try {
            return ResponseEntity.ok(ICarModelService.page(current, size, name));
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
     * @param carModel
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> removeByBrandId(@RequestBody CarModel carModel) {
        try {
            ICarModelService.removeByBrandId(carModel.getId());
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("软删除失败");
        }
    }

    /**
     * 更改
     *
     * @param carModel
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarModel carModel) {
        try {
            ICarModelService.update(carModel);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("修改失败");
        }
    }

    /**
     * 增加
     *
     * @param carModel
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarModel carModel) {
        try {
            ICarModelService.save(carModel);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("add失败");
        }
    }
}
