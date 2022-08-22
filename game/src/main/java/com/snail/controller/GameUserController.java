package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.entity.GameUser;
import com.snail.service.IGameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class GameUserController extends BaseController<GameUser> {

    @Autowired
    private IGameUserService gameUserService;

    @GetMapping("getByGroupId")
    public ResponseEntity<Object> getBy(@RequestParam("groupId") Long groupId) {
        QueryWrapper<GameUser> gameUserQueryWrapper = new QueryWrapper<>();
        gameUserQueryWrapper.eq("GROUP_ID", groupId);
        return ResponseEntity.ok(gameUserService.list(gameUserQueryWrapper));
    }
}
