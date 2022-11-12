package com.snail.proxy.controller;

import com.snail.conreoller.BaseController;
import com.snail.proxy.entity.Server;
import com.snail.proxy.service.IServerService;
import com.snail.proxy.util.WebSshHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/server")
public class ServerController extends BaseController<Server> {

    @Autowired
    private IServerService serverService;

    @PostMapping("/connect")
    public ResponseEntity<Object> connect(@RequestBody Server server) {
        server = serverService.getById(server.getId());
        WebSshHandler.serverInfoMap.put(server.getId(), server);
        return ResponseEntity.ok(null);
    }

    @GetMapping("refresh")
    public ResponseEntity<Object> refreshXray(){
        try {
            serverService.refreshXray();
            return ResponseEntity.ok(null);
        } catch (Exception e){
            log.error("refresh xray error：", e);
            return ResponseEntity.ok(null);
        }
    }
}
