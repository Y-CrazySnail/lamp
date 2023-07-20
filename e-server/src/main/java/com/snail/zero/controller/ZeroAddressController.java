package com.snail.zero.controller;

import com.snail.conreoller.BaseController;
import com.snail.utils.OauthUtils;
import com.snail.zero.entity.ZeroAddress;
import com.snail.zero.service.IZeroAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/zero-address")
public class ZeroAddressController extends BaseController<ZeroAddress> {

    @Autowired
    private IZeroAddressService zeroAddressService;

    @GetMapping("listByUsername")
    public ResponseEntity<Object> listByUsername() {
        try {
            String username = OauthUtils.getUsername();
            if (StringUtils.isEmpty(username)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no login");
            }
            return ResponseEntity.ok(zeroAddressService.listByUsername(username));
        } catch (Exception e) {
            log.error("list address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("list address error");
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody ZeroAddress zeroAddress) {
        try {
            zeroAddressService.save(zeroAddress);
            return ResponseEntity.ok("save address success");
        } catch (Exception e) {
            log.error("save address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("save address error");
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody ZeroAddress zeroAddress) {
        try {
            zeroAddressService.update(zeroAddress);
            return ResponseEntity.ok("update address success");
        } catch (Exception e) {
            log.error("update address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("update address error");
        }
    }

    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody ZeroAddress zeroAddress) {
        try {
            if (StringUtils.isEmpty(zeroAddress.getId())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("address id is null");
            }
            zeroAddressService.removeById(zeroAddress);
            return ResponseEntity.ok("remove address success");
        } catch (Exception e) {
            log.error("remove address error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("remove address error");
        }
    }
}
