package com.snail.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.snail.car_film_saas.entity.CarBrand;
import com.snail.car_film_saas.entity.CarModel;
import com.snail.car_film_saas.service.ICarModelService;
import com.snail.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car-model")
public class CarModelController extends BaseController<CarBrand> {
    @Autowired
   private ICarModelService ICarModelService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(ICarModelService.list());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    /**
     * 分页模糊查询
     * @param current
     * @param size
     * @return
     */
<<<<<<< HEAD
    @GetMapping("/page")
    public ResponseEntity<Object> page(@RequestParam("current") int current, @RequestParam("size") int size,@RequestParam("name") String name) {
=======
    @GetMapping("/pages")
    public ResponseEntity<Object> page(@RequestParam("current") int current, @RequestParam("size") int size) {
>>>>>>> a8f0e212e61f8a31c2b848b3362dcbc62625e360
        try {
            return ResponseEntity.ok(ICarModelService.page(current, size,name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    /**
     * id查单个
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id){
        try {
            if (!ICarModelService.getById(id).getDeleteFlag()){
                return ResponseEntity.ok(ICarModelService.getById(id));
            }else {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不可查询软删除用户");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 根据brand_id删除
     * @param carModel
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> removeByBrandId(@RequestBody CarModel carModel){
        try {
            ICarModelService.removeByBrandId(carModel.getId());
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
    public ResponseEntity<Object> update(@RequestBody CarModel carModel){
        try {
            ICarModelService.update(carModel);
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
    public ResponseEntity<Object> save(@RequestBody CarModel carModel){
        try {
            ICarModelService.save(carModel);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("add失败");
        }
    }
}
