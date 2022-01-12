package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Traffic;
import com.snail.service.ITrafficService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/traffic")
public class TrafficController extends BaseController<Traffic> {

    @Autowired
    private ITrafficService trafficService;

    @PostMapping("save")
    @ApiOperation(value = "保存接口")
    public ResponseEntity<Object> save(@RequestBody Map<String, Object> map) {
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) map.get("stat");
        infoList.forEach(info -> {
            String name = (String) info.get("name");
            if (name.contains("@vhfugv.com")) {
                String id = new String(Base64.decodeBase64(name.split(">>>")[1].split("@")[0]));
                String type = name.split(">>>")[3];
                Long value = Long.valueOf(String.valueOf(info.get("value")));
                Traffic traffic = new Traffic();
                traffic.setTraffic(value);
                traffic.setMemberId(Long.valueOf(id));
                traffic.setType(type);
                traffic.setFlag(0);
                trafficService.save(traffic);
            }
        });
        return ResponseEntity.ok("success");
    }
}
