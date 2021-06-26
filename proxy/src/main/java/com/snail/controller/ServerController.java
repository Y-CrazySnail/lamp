package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Server;
import com.snail.service.IServerService;
import com.snail.util.WebSshHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
}
