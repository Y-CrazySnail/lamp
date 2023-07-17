package com.snail.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.snail.car_film_saas.entity.CarBrand;
import com.snail.car_film_saas.service.ICarBrandService;
import com.snail.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-brand")
public class CarBrandController extends BaseController<CarBrand> {
    @Autowired
    private ICarBrandService carBrandService;

    /**
     * 查询所有
     *
     * @return
     */
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
     * @param brandName
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<Object> page(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam("brandName") String brandName) {
        try {
            return ResponseEntity.ok(carBrandService.page(current, size, brandName));
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
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (StringUtils.isEmpty(id)) {
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
     * @param carBrand
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarBrand carBrand) {
        try {
            carBrandService.remove(carBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("软删除失败");
        }
    }

    /**
     * 更改
     *
     * @param carBrand
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarBrand carBrand) {
        try {
            if (carBrand.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("品牌名为空");
            }
            if (carBrand.getLogoPath().isEmpty()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("log文件路径为空");
            }
            carBrandService.update(carBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("修改失败");
        }
    }

    /**
     * 新增
     *
     * @param carBrand
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarBrand carBrand) {
        try {
            if (carBrand.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("品牌名为空");
            }
            if (carBrand.getLogoPath().isEmpty()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("log文件路径为空");
            }
            carBrandService.save(carBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("add失败");
        }
    }
}
