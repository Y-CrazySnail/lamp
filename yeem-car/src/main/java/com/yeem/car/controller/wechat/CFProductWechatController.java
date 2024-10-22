package com.yeem.car.controller.wechat;

import com.yeem.car.entity.CarFilmUser;
import com.yeem.car.service.ICarFilmUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wechat/user")
public class CFProductWechatController {

    @Autowired
    private ICarFilmUserService carFilmUserService;

    /**
     * 小程序登录
     *
     * @return 登录信息
     * @apiNote 小程序登录
     */
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody CarFilmUser carFilmUser) {
        try {
            return ResponseEntity.ok(carFilmUserService.login(carFilmUser));
        } catch (Exception e) {
            log.error("wx login api error：", e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
