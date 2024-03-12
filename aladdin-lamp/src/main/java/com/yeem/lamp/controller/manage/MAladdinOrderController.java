package com.yeem.lamp.controller.manage;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinOrder;
import com.yeem.lamp.service.IAladdinOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/order")
public class MAladdinOrderController {

    @Autowired
    private IAladdinOrderService aladdinOrderService;

    /**
     * 列表查询
     *
     * @return 列表信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(aladdinOrderService.list());
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询列表失败");
        }
    }

    /**
     * 分页查询
     *
     * @param current 页码
     * @param size    页容量
     * @return 分页信息
     */
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current,
                                        @RequestParam("size") int size) {
        try {
            return ResponseEntity.ok(aladdinOrderService.pages(current, size));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询失败");
        }
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 会员信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            if (null == id) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询 id为空");
            }
            return ResponseEntity.ok(aladdinOrderService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 更改
     *
     * @param aladdinOrder aladdinOrder
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AladdinOrder aladdinOrder) {
        try {
            aladdinOrderService.updateById(aladdinOrder);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更新失败");
        }
    }

    /**
     * 新增
     *
     * @param aladdinOrder aladdinOrder
     * @return 新增结果
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody AladdinOrder aladdinOrder) {
        try {
            aladdinOrderService.place(aladdinOrder);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("新增失败");
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<Object> pay(@RequestBody AladdinOrder aladdinOrder) {
        return ResponseEntity.ok(aladdinOrderService.pay(aladdinOrder));
    }

    /**
     * 删除
     *
     * @param aladdinOrder aladdinOrder
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody AladdinOrder aladdinOrder) {
        try {
            if (null == aladdinOrder.getId()) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
            }
            UpdateWrapper<AladdinOrder> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
            updateWrapper.eq(BaseEntity.BaseField.ID.getName(), aladdinOrder.getId());
            aladdinOrderService.update(updateWrapper);
            return ResponseEntity.ok("删除");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
