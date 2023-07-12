package com.snail.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.snail.car_film_saas.entity.CarFilmProduct;
import com.snail.car_film_saas.service.CarFilmProductServer;
import com.snail.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carfilmproduct")

public class CarFilmProductController extends BaseController<CarFilmProduct> {
    @Autowired
    private CarFilmProductServer carFilmProductServer;

    /**
     * 查询不被软删除的数据
     *
     * @return
     */
    @GetMapping("/getall")
    public ResponseEntity<Object> listProduct() {
        try {
            return ResponseEntity.ok(carFilmProductServer.listProduct());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    /**
     * 分页查询不被软删除的数据
     *
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/pageget")
    public ResponseEntity<Object> listProductPage(@RequestParam("current") int current, @RequestParam("size") int size) {
        try {
            return ResponseEntity.ok(carFilmProductServer.listProductPage(current, size));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    /**
     * 按id查询不被软删除的数据 如果输入被软删除的 则返回:不可以查询到软删除的用户
     *
     * @param id
     * @return
     */
    @GetMapping("/getid")
    public ResponseEntity<Object> listProductById(@RequestParam("id") Long id) {
        try {
            //flag不是ture 可以显示
            if (!carFilmProductServer.listProductById(id).getDeleteFlag()) {
                return ResponseEntity.ok(carFilmProductServer.listProductById(id));
            } else {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不可以查询到软删除的用户");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 软删除
     *
     * @param carFilmProduct
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> removeProduct(@RequestBody CarFilmProduct carFilmProduct) {
        try {
            carFilmProductServer.removeProduct(carFilmProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }

    }

    /**
     * 增加
     *
     * @param carFilmProduct
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveProduct(@RequestBody CarFilmProduct carFilmProduct) {
        try {
            carFilmProductServer.saveProduct(carFilmProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    /**
     * 更改商品
     *
     * @param carFilmProduct
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateProduct(@RequestBody CarFilmProduct carFilmProduct) {
        try {
            carFilmProductServer.updateProduct(carFilmProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
