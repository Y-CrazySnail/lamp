package com.snail.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.snail.car_film_saas.entity.CarBrand;
import com.snail.car_film_saas.entity.CarModel;
import com.snail.car_film_saas.service.CarModelServer;
import com.snail.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carmodel")
public class CarModelController extends BaseController<CarBrand> {
    @Autowired
   private CarModelServer carModelServer;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/getall")
    public ResponseEntity<Object> getlist() {
        try {
            return ResponseEntity.ok(carModelServer.listModelBy());
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
            return ResponseEntity.ok(carModelServer.listModelByPage(current, size));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
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
            if (!carModelServer.ModelById(id).getDeleteFlag()){
                return ResponseEntity.ok(carModelServer.ModelById(id));
            }else {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不可查询软删除用户");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 删除
     * @param carModel
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> removeBrand(@RequestBody CarModel carModel){
        try {
            carModelServer.remove(carModel.getId());
                return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("软删除失败");
        }
    }

    /**
     * 更改
     * @param carModel
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateBrand(@RequestBody CarModel carModel){
        try {
            carModelServer.updateCarModel(carModel);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("修改失败");
        }
    }

    /**
     * 增加
     * @param carModel
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBrand(@RequestBody CarModel carModel){
        try {
            carModelServer.saveCarModel(carModel);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("add失败");
        }
    }
}
