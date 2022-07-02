package com.snail.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.snail.conreoller.BaseController;
import com.snail.entity.GameCampaignDetail;
import com.snail.service.IGameCampaignDetailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/campaign_detail")
public class GameCampaignDetailController extends BaseController<GameCampaignDetail> {

    @Autowired
    private IGameCampaignDetailService gameCampaignDetailService;

    //表格导出接口
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response,
                       Long groupId,
                       Long campaignId,
                       Integer mainReport,
                       Integer siegeReport) throws IOException {
        QueryWrapper<GameCampaignDetail> gameCampaignDetailQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(groupId)) {
            gameCampaignDetailQueryWrapper.eq("GROUP_ID", groupId);
        }
        if (!StringUtils.isEmpty(campaignId)) {
            gameCampaignDetailQueryWrapper.eq("CAMPAIGN_ID", campaignId);
        }
        if (!StringUtils.isEmpty(mainReport)) {
            gameCampaignDetailQueryWrapper.eq("MAIN_REPORT", mainReport);
        }
        if (!StringUtils.isEmpty(siegeReport)) {
            gameCampaignDetailQueryWrapper.eq("SIEGE_REPORT", siegeReport);
        }
        List<GameCampaignDetail> gameCampaignDetailList = gameCampaignDetailService.list(gameCampaignDetailQueryWrapper);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("campaignName", "战役名称");
        writer.addHeaderAlias("groupName", "分组名称");
        writer.addHeaderAlias("userName", "玩家名称");
        writer.addHeaderAlias("mainArmyNumber", "主力数量");
        writer.addHeaderAlias("mainArmyLevel", "主力等级");
        writer.addHeaderAlias("siegeArmyNumber", "器械数量");
        writer.addHeaderAlias("siegeArmyLevel", "器械等级");
        writer.addHeaderAlias("siegeScoreEach", "器械攻城值");
        writer.addHeaderAlias("tentPosition", "营帐位置");
        writer.addHeaderAlias("workFlag", "是否出勤");
        writer.addHeaderAlias("absentReason", "缺席原因");
        writer.addHeaderAlias("mainReport", "主力战报");
        writer.addHeaderAlias("siegeReport", "器械战报");
        //默认配置
        writer.write(gameCampaignDetailList, true);
        //设置content—type
        response.setContentType("application/vnd.ms-excel");
        //设置标题
        String fileName = URLEncoder.encode("战役人员信息", "UTF-8");
        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        //将Writer刷新到OutPut
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }

    @GetMapping("pageByCondition")
    @ApiOperation(value = "分页查询接口")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          Long groupId,
                                          Long campaignId,
                                          Integer mainReport,
                                          Integer siegeReport,
                                          QueryWrapper<GameCampaignDetail> queryWrapper) {
        if (!StringUtils.isEmpty(groupId)) {
            queryWrapper.eq("GROUP_ID", groupId);
        }
        if (!StringUtils.isEmpty(campaignId)) {
            queryWrapper.eq("CAMPAIGN_ID", campaignId);
        }
        if (!StringUtils.isEmpty(mainReport)) {
            queryWrapper.eq("MAIN_REPORT", mainReport);
        }
        if (!StringUtils.isEmpty(siegeReport)) {
            queryWrapper.eq("SIEGE_REPORT", siegeReport);
        }
        return super.getPage(current, size, queryWrapper);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存接口")
    public ResponseEntity<Object> save(@RequestBody GameCampaignDetail entity) {
        QueryWrapper<GameCampaignDetail> gameCampaignDetailQueryWrapper = new QueryWrapper<>();
        gameCampaignDetailQueryWrapper.eq("CAMPAIGN_ID", entity.getCampaignId());
        gameCampaignDetailQueryWrapper.eq("GROUP_ID", entity.getGroupId());
        gameCampaignDetailQueryWrapper.eq("USER_ID", entity.getUserId());
        GameCampaignDetail gameCampaignDetail = gameCampaignDetailService.getOne(gameCampaignDetailQueryWrapper);
        if (StringUtils.isEmpty(gameCampaignDetail)) {
            super.save(entity);
        } else {
            UpdateWrapper<GameCampaignDetail> gameCampaignDetailUpdateWrapper = new UpdateWrapper<>();
            gameCampaignDetailUpdateWrapper.eq("CAMPAIGN_ID", entity.getCampaignId());
            gameCampaignDetailUpdateWrapper.eq("GROUP_ID", entity.getGroupId());
            gameCampaignDetailUpdateWrapper.eq("USER_ID", entity.getUserId());
            gameCampaignDetailUpdateWrapper.set("MAIN_ARMY_NUMBER", entity.getMainArmyNumber());
            gameCampaignDetailUpdateWrapper.set("MAIN_ARMY_LEVEL", entity.getMainArmyLevel());
            gameCampaignDetailUpdateWrapper.set("SIEGE_ARMY_NUMBER", entity.getSiegeArmyNumber());
            gameCampaignDetailUpdateWrapper.set("SIEGE_ARMY_LEVEL", entity.getSiegeArmyLevel());
            gameCampaignDetailUpdateWrapper.set("SIEGE_SCORE_EACH", entity.getSiegeScoreEach());
            gameCampaignDetailUpdateWrapper.set("TENT_POSITION", entity.getTentPosition());
            gameCampaignDetailService.update(gameCampaignDetailUpdateWrapper);
        }
        return ResponseEntity.ok(entity);
    }

    @GetMapping("get")
    @ApiOperation(value = "根据接口")
    public ResponseEntity<Object> get(String campaignName, String groupName, String userName) {
        QueryWrapper<GameCampaignDetail> gameCampaignDetailQueryWrapper = new QueryWrapper<>();
        gameCampaignDetailQueryWrapper.eq("CAMPAIGN_NAME", campaignName);
        gameCampaignDetailQueryWrapper.eq("GROUP_NAME", groupName);
        gameCampaignDetailQueryWrapper.eq("USER_NAME", userName);
        GameCampaignDetail gameCampaignDetail = gameCampaignDetailService.getOne(gameCampaignDetailQueryWrapper);
        return ResponseEntity.ok(gameCampaignDetail);
    }
}
