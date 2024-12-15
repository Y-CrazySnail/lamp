package com.yeem.lamp.controller.web;

import com.yeem.lamp.entity.LampClientTraffic;
import com.yeem.lamp.service.LampClientTrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-client-traffic")
public class LampClientTrafficController {

    @Autowired
    private LampClientTrafficService lampClientTrafficService;

    @GetMapping
    public List<LampClientTraffic> getAllLampClientTraffic() {
        return lampClientTrafficService.list();
    }

    @GetMapping("/{id}")
    public LampClientTraffic getLampClientTrafficById(@PathVariable Long id) {
        return lampClientTrafficService.getById(id);
    }

    @PostMapping
    public LampClientTraffic createLampClientTraffic(@RequestBody LampClientTraffic lampClientTraffic) {
        return null;
    }

    @PutMapping("/{id}")
    public LampClientTraffic updateLampClientTraffic(@PathVariable Long id, @RequestBody LampClientTraffic lampClientTraffic) {
        lampClientTraffic.setId(id);
        return lampClientTrafficService.updateById(lampClientTraffic) ? lampClientTraffic : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampClientTraffic(@PathVariable Long id) {
        return lampClientTrafficService.removeById(id);
    }
}
