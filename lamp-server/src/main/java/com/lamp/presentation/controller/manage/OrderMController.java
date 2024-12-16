package com.lamp.presentation.controller.manage;

import cn.hutool.http.HttpStatus;
import com.lamp.application.dto.OrderDTO;
import com.lamp.application.service.manage.OrderManageAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/order")
public class OrderMController {

    @Autowired
    private OrderManageAppService orderAppService;

    /**
     * 订单-列表查询
     *
     * @return 列表信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(orderAppService.list(new OrderDTO()));
        } catch (Exception e) {
            log.error("订单-列表查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("订单-列表查询失败");
        }
    }

    /**
     * 订单-分页查询
     *
     * @param current 页码
     * @param size    页容量
     * @return 分页信息
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> page(@RequestParam("current") int current,
                                       @RequestParam("size") int size) {
        try {
            return ResponseEntity.ok(orderAppService.page(current, size));
        } catch (Exception e) {
            log.error("订单-分页查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("订单-分页查询失败");
        }
    }

    /**
     * 订单-根据ID查询
     *
     * @param id ID
     * @return 订单信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (null == id) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("订单-根据ID查询失败:ID为空");
            }
            return ResponseEntity.ok(orderAppService.getById(id));
        } catch (Exception e) {
            log.error("订单-根据ID查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("订单-根据ID查询失败");
        }
    }

    /**
     * 订单-更新
     *
     * @param orderDTO 订单信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody OrderDTO orderDTO) {
        try {
            orderAppService.updateById(orderDTO);
            return ResponseEntity.ok("订单-更新成功");
        } catch (Exception e) {
            log.error("订单-更新失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("订单-更新失败");
        }
    }

    /**
     * 订单-删除
     *
     * @param orderDTO 订单信息
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody OrderDTO orderDTO) {
        try {
            if (null == orderDTO.getId()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("订单-删除失败");
            }
            orderAppService.removeById(orderDTO.getId());
            return ResponseEntity.ok("订单-删除成功");
        } catch (Exception e) {
            log.error("订单-删除失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("订单-删除失败");
        }
    }
}
