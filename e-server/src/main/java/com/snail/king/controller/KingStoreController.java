package com.snail.king.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.king.entity.KingStore;
import com.snail.king.service.IKingStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/king-store")
public class KingStoreController extends BaseController<KingStore> {

    @Autowired
    private IKingStoreService kingStoreService;

    @GetMapping("get")
    public ResponseEntity<Object> get(KingStore kingStore) {
        QueryWrapper<KingStore> kingStoreQueryWrapper = new QueryWrapper<>();
        kingStoreQueryWrapper.eq("province", kingStore.getProvince());
        kingStoreQueryWrapper.eq("city", kingStore.getCity());
        kingStoreQueryWrapper.eq("county", kingStore.getCounty());
        List<KingStore> kingStoreList = kingStoreService.list(kingStoreQueryWrapper);
        return ResponseEntity.ok(kingStoreList);
    }
}
