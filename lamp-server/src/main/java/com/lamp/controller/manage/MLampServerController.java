package com.lamp.controller.manage;

import com.lamp.entity.LampServer;
import com.lamp.service.manage.MLampServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage/lamp-server")
public class MLampServerController {

    @Autowired
    private MLampServerService lampServerService;

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
