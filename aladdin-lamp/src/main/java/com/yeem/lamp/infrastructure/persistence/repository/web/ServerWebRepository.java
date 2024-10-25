package com.yeem.lamp.infrastructure.persistence.repository.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.infrastructure.persistence.entity.ServerDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.ServerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ServerWebRepository {

    @Autowired
    private ServerMapper serverMapper;

    public List<Server> list() {
        LambdaQueryWrapper<ServerDo> queryWrapper = new LambdaQueryWrapper<>();
        List<ServerDo> serverDoList = serverMapper.selectList(queryWrapper);
        return serverDoList.stream().map(ServerDo::convertServer).collect(Collectors.toList());
    }

    public Server getById(Long id) {
        ServerDo serverDo = serverMapper.selectById(id);
        return serverDo.convertServer();
    }
}
