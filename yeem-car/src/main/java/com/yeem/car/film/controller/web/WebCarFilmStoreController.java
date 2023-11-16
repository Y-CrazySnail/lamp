package com.yeem.car.film.controller.web;


import cn.hutool.http.HttpStatus;
import com.yeem.car.film.entity.CarFilmStore;
import com.yeem.car.film.log.OperateLog;
import com.yeem.car.film.service.ICarFilmStoreService;
import com.yeem.car.film.service.impl.CarFilmStoreServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/car-film-store")
public class WebCarFilmStoreController {
    @Autowired
    private ICarFilmStoreService carFilmStoreService;

    @Autowired
    private CarFilmStoreServiceImpl carFilmStoreServiceI;

    @OperateLog(operateModule = "store模块", operateType = "list查询", operateDesc = "描述:查询store全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "province", required = false) String province, @RequestParam(value = "city", required = false) String city, @RequestParam(value = "county", required = false) String county, @RequestParam(value = "contactName", required = false) String contactName, @RequestParam(value = "contactPhone", required = false) String contactPhone) {
        try {
            return ResponseEntity.ok(carFilmStoreService.list(productNo, name, province, city, county, contactName, contactPhone));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @OperateLog(operateModule = "store模块", operateType = "pages查询", operateDesc = "描述:分页查询store全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "province", required = false) String province, @RequestParam(value = "city", required = false) String city, @RequestParam(value = "county", required = false) String county, @RequestParam(value = "contactName", required = false) String contactName, @RequestParam(value = "contactPhone", required = false) String contactPhone) {
        try {
            return ResponseEntity.ok(carFilmStoreService.pages(current, size, productNo, name, province, city, county, contactName, contactPhone));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @OperateLog(operateModule = "store模块", operateType = "getById查询", operateDesc = "描述:ID查询store全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(carFilmStoreService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @OperateLog(operateModule = "store模块", operateType = "delete", operateDesc = "描述:软删除store全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmStore carFilmStore) {
        try {
            carFilmStoreService.remove(carFilmStore);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @OperateLog(operateModule = "store模块", operateType = "save", operateDesc = "描述:保存store全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmStore carFilmStore) {
        try {
            carFilmStoreService.save(carFilmStore);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @OperateLog(operateModule = "store模块", operateType = "update", operateDesc = "描述:修改store全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmStore carFilmStore) {
        try {
            carFilmStoreService.update(carFilmStore);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }

    @OperateLog(operateModule = "store模块", operateType = "distance", operateDesc = "描述:输入经纬度计算最近的10个门店")
    @GetMapping("/distance")
    public ResponseEntity<Object> tete(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude) {
        try {
            return ResponseEntity.ok(carFilmStoreServiceI.selectAddress(latitude, longitude));
        } catch (Exception e) {
            log.error("distance方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("经纬度查询距离失败");
        }
    }
}
