package com.yeem.car.controller.manager;

import cn.hutool.http.HttpStatus;
import com.yeem.car.entity.CarFilmTenant;
import com.yeem.car.entity.CarFilmUser;
import com.yeem.car.log.OperateLog;
import com.yeem.car.service.ICarFilmUserService;
import com.yeem.common.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-film-user")
public class CarFilmUserController extends BaseController<CarFilmUser> {
    @Autowired
    private ICarFilmUserService carFilmUserService;

    /**
     * 分页查询不被软删除的数据
     *
     * @param current 页码
     * @param size    页容量
     * @return
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current,
                                        @RequestParam("size") int size,
                                        @RequestParam(value = "productNo", required = false) String productNo,
                                        @RequestParam(value = "nickName", required = false) String nickName,
                                        @RequestParam(value = "phone", required = false) String phone) {
        try {
            return ResponseEntity.ok(carFilmUserService.pages(current, size, productNo, nickName, phone));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    /**
     * 更改用户
     *
     * @param carFilmUser
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmUser carFilmUser) {
        try {
            carFilmUserService.updateById(carFilmUser);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
