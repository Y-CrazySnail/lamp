package com.yeem.car.film.controller.manager;

import cn.hutool.http.HttpStatus;
import com.yeem.car.film.entity.CarFilmTenant;
import com.yeem.car.film.log.OperateLog;
import com.yeem.common.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-film-tenant")
public class CarFilmTenantController extends BaseController<CarFilmTenant> {
    @Autowired
    private com.yeem.car.film.service.ICarFilmTenantService ICarFilmTenantService;

    /**
     * 查询不被软删除的数据
     *
     * @return
     */
    @OperateLog(operateModule = "product模块", operateType = "list查询", operateDesc = "描述:查询product全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "productNo", required = false) String productNo,
                                       @RequestParam(value = "productName", required = false) String productName,
                                       @RequestParam(value = "companyName", required = false) String companyName,
                                       @RequestParam(value = "companyNo", required = false) String companyNo,
                                       @RequestParam(value = "managerName", required = false) String managerName,
                                       @RequestParam(value = "managerPhone", required = false) String managerPhone,
                                       @RequestParam(value = "miniProgramFlag", required = false) String miniProgramFlag,
                                       @RequestParam(value = "officialWebsiteFlag", required = false) String officialWebsiteFlag) {
        try {
            return ResponseEntity.ok(ICarFilmTenantService.list(productNo, productName, companyName,
                    companyNo, managerName, managerPhone, miniProgramFlag, officialWebsiteFlag));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("k查询所有失败");
        }
    }

    /**
     * 分页查询不被软删除的数据
     *
     * @param current
     * @param size
     * @return
     */
    @OperateLog(operateModule = "product模块", operateType = "pages查询", operateDesc = "描述:分页查询product全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current,
                                        @RequestParam("size") int size,
                                        @RequestParam(value = "productNo", required = false) String productNo,
                                        @RequestParam(value = "productName", required = false) String productName,
                                        @RequestParam(value = "companyName", required = false) String companyName,
                                        @RequestParam(value = "companyNo", required = false) String companyNo,
                                        @RequestParam(value = "managerName", required = false) String managerName,
                                        @RequestParam(value = "managerPhone", required = false) String managerPhone,
                                        @RequestParam(value = "miniProgramFlag", required = false) String miniProgramFlag,
                                        @RequestParam(value = "officialWebsiteFlag", required = false) String officialWebsiteFlag) {
        try {
            return ResponseEntity.ok(ICarFilmTenantService.pages(current, size, productNo, productName,
                    companyName, companyNo, managerName, managerPhone, miniProgramFlag, officialWebsiteFlag));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    /**
     * 按id查询不被软删除的数据 如果输入被软删除的 则返回:不可以查询到软删除的用户
     *
     * @param id
     * @return
     */
    @OperateLog(operateModule = "product模块", operateType = "getById查询", operateDesc = "描述:ID查询product全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            //flag不是ture 可以显示
            if (!ICarFilmTenantService.getById(id).getDeleteFlag()) {
                return ResponseEntity.ok(ICarFilmTenantService.getById(id));
            } else {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不可以查询到软删除的用户");
            }
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 软删除
     *
     * @param carFilmProduct
     * @return
     */
    @OperateLog(operateModule = "product模块", operateType = "delete", operateDesc = "描述:软删除product全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmTenant carFilmProduct) {
        try {
            ICarFilmTenantService.remove(carFilmProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    /**
     * 增加
     *
     * @param carFilmProduct
     * @return
     */
    @OperateLog(operateModule = "product模块", operateType = "save", operateDesc = "描述:保存product全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmTenant carFilmProduct) {
        try {
            ICarFilmTenantService.save(carFilmProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    /**
     * 更改商品
     *
     * @param carFilmTenant
     * @return
     */
    @OperateLog(operateModule = "product模块", operateType = "update", operateDesc = "描述:修改product全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmTenant carFilmTenant) {
        try {
            ICarFilmTenantService.update(carFilmTenant);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
