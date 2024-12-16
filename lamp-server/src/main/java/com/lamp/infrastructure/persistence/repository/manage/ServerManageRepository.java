package com.lamp.infrastructure.persistence.repository.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.common.entity.BaseEntity;
import com.lamp.domain.entity.Server;
import com.lamp.infrastructure.persistence.entity.ServerDo;
import com.lamp.infrastructure.persistence.repository.mapper.ServerMapper;
import com.lamp.security.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ServerManageRepository {

    @Autowired
    private ServerMapper serverMapper;

    public List<Server> list() {
        LambdaQueryWrapper<ServerDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServerDo::getDeleteFlag, false);
        List<ServerDo> serverDoList = serverMapper.selectList(queryWrapper);
        return serverDoList.stream().map(ServerDo::convertServer).collect(Collectors.toList());
    }

    public Server getById(Long id) {
        ServerDo serverDo = serverMapper.selectById(id);
        return serverDo.convertServer();
    }

    public void save(Server server) {
        ServerDo serverDo = ServerDo.init(server);
        serverMapper.insert(serverDo);
    }

    public void updateById(Server server) {
        ServerDo serverDo = ServerDo.init(server);
        serverMapper.updateById(serverDo);
    }

    public void removeById(Long id) {
        LambdaUpdateWrapper<ServerDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ServerDo::getDeleteFlag, true);
        updateWrapper.eq(ServerDo::getId, id);
        serverMapper.update(null, updateWrapper);
    }

    public IPage<Server> pages(int current, int size) {
        QueryWrapper<ServerDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<ServerDo> page = new Page<>(current, size);
        page = serverMapper.selectPage(page, queryWrapper);
        IPage<Server> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(ServerDo::convertServer).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }
}
