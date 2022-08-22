package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.entity.Node;
import com.snail.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/node")
public class NodeController extends BaseController<Node> {

    @Autowired
    private INodeService nodeService;

    @GetMapping("getValid")
    public ResponseEntity<Object> getById(Long id) {
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.le("member_id", 0);
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        return ResponseEntity.ok(nodeList);
    }

    @GetMapping("getByMemberId")
    public ResponseEntity<Object> getByMemberId(Long id) {
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq("member_id", id);
        List<Node> nodeList = nodeService.list(nodeQueryWrapper);
        return ResponseEntity.ok(nodeList);
    }

    @DeleteMapping("deleteByMemberId")
    public ResponseEntity<Object> deleteByMemberId(@RequestBody Node entity) {
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq("member_id", entity.getMemberId());
        nodeService.remove(nodeQueryWrapper);
        return ResponseEntity.ok("删除成功");
    }
}
