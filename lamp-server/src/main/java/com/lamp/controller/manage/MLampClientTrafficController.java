package com.lamp.controller.manage;

import com.lamp.entity.LampClientTraffic;
import com.lamp.service.manage.MLampClientTrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage/lamp-client-traffic")
public class MLampClientTrafficController {

    @Autowired
    private MLampClientTrafficService clientTrafficService;

    @GetMapping
    public List<LampClientTraffic> getAllLampClientTraffic() {
        return clientTrafficService.list();
    }

    @GetMapping("/{id}")
    public LampClientTraffic getLampClientTrafficById(@PathVariable Long id) {
        return clientTrafficService.getById(id);
    }

    @PostMapping
    public LampClientTraffic createLampClientTraffic(@RequestBody LampClientTraffic lampClientTraffic) {
        return null;
    }

    @PutMapping("/{id}")
    public LampClientTraffic updateLampClientTraffic(@PathVariable Long id, @RequestBody LampClientTraffic lampClientTraffic) {
        lampClientTraffic.setId(id);
        return clientTrafficService.updateById(lampClientTraffic) ? lampClientTraffic : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampClientTraffic(@PathVariable Long id) {
        return clientTrafficService.removeById(id);
    }
}
