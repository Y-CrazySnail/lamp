package com.snail.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.snail.car_film_saas.entity.CarBrand;
import com.snail.car_film_saas.service.CarBrandServer;
import com.snail.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carbrand")
public class CarBrandController extends BaseController<CarBrand> {
    @Autowired
    private CarBrandServer carBrandServer;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/getall")
    public ResponseEntity<Object> getlist() {
        try {
            return ResponseEntity.ok(carBrandServer.listBrand());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    /**
     * 分页查所有
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/pagegetall")
    public ResponseEntity<Object> listBrandPage(@RequestParam("current") int current, @RequestParam("size") int size) {
        try {
            return ResponseEntity.ok(carBrandServer.listBrandPage(current, size));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    /**
     * 分页模糊查询
     * @param current
     * @param size
     * @param brandName
     * @return
     */
    @GetMapping("/likepage")
    public ResponseEntity<Object> listLikeBrandPage(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam("brandName") String brandName){
        try{
            return ResponseEntity.ok(carBrandServer.listLikeBrandPage(current,size,brandName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页模糊查询失败");
        }
    }

    /**
     * id查单个
     * @param id
     * @return
     */
    @GetMapping("/getid")
    public ResponseEntity<Object> BrandById(@RequestParam("id") Long id){
        try {
            if (!carBrandServer.BrandById(id).getDeleteFlag()){
                return ResponseEntity.ok(carBrandServer.BrandById(id));
            }else {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不可查询软删除用户");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 删除
     * @param carBrand
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> removeBrand(@RequestBody CarBrand carBrand){
        try {
            carBrandServer.remove(carBrand);
                return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("软删除失败");
        }
    }

    /**
     * 更改
     * @param carBrand
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateBrand(@RequestBody CarBrand carBrand){
        try {
            if (carBrand.getName().isEmpty()){
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("品牌名为空");
            }
            if (carBrand.getLogoPath().isEmpty()){
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("log文件路径为空");
            }
            carBrandServer.updateBrand(carBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("修改失败");
        }
    }

    /**
     * 新增
     * @param carBrand
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBrand(@RequestBody CarBrand carBrand){
        try {
            if (carBrand.getName().isEmpty()){
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("品牌名为空");
            }
            if (carBrand.getLogoPath().isEmpty()){
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("log文件路径为空");
            }
            carBrandServer.saveBrand(carBrand);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("add失败");
        }
    }
}
