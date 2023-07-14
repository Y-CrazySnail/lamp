package com.snail.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.snail.car_film_saas.entity.CarBrand;
import com.snail.car_film_saas.service.CarBrandService;
import com.snail.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car-brand")
public class CarBrandController extends BaseController<CarBrand> {
    @Autowired
    private CarBrandService carBrandService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(carBrandService.listBrand());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    /**
     * 分页查所有
     *
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<Object> page(@RequestParam("current") int current, @RequestParam("size") int size) {
        try {
            return ResponseEntity.ok(carBrandService.listBrandPage(current, size));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
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
    @GetMapping("/like")
    public ResponseEntity<Object> like(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam("brandName") String brandName) {
        try {
            return ResponseEntity.ok(carBrandService.listLikeBrandPage(current, size, brandName));
        } catch (Exception e) {
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
            if (!carBrandService.BrandById(id).getDeleteFlag()) {
                return ResponseEntity.ok(carBrandService.BrandById(id));
            } else {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不可查询软删除用户");
            }
        } catch (Exception e) {
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
            carBrandService.updateBrand(carBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
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
            carBrandService.saveBrand(carBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("add失败");
        }
    }
}
