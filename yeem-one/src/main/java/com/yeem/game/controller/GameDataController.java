package com.yeem.game.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.game.service.IGameDataService;
import com.yeem.one.entity.OneCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 管理端-战功
 */
@Slf4j
@RestController
@RequestMapping("/game/data")
public class GameDataController {

    @Autowired
    IGameDataService gameDataService;

    @GetMapping("upload")
    public ResponseEntity<IPage<OneCategory>> upload(@RequestPart("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String fileContent = StreamUtils.copyToString(file.getInputStream(), StandardCharsets.UTF_8);
                gameDataService.deal(fileContent);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
