package com.yeem.lamp.controller.web;

import com.yeem.lamp.entity.LampServer;
import com.yeem.lamp.service.LampServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-server")
public class LampServerController {

    @Autowired
    private LampServerService lampServerService;

    @GetMapping
    public List<LampServer> getAllLampServers() {
        return lampServerService.list();
    }

    @GetMapping("/{id}")
    public LampServer getLampServerById(@PathVariable Long id) {
        return lampServerService.getById(id);
    }

    @PostMapping
    public LampServer createLampServer(@RequestBody LampServer lampServer) {
        return null;
    }

    @PutMapping("/{id}")
    public LampServer updateLampServer(@PathVariable Long id, @RequestBody LampServer lampServer) {
        lampServer.setId(id);
        return lampServerService.updateById(lampServer) ? lampServer : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampServer(@PathVariable Long id) {
        return lampServerService.removeById(id);
    }
}
