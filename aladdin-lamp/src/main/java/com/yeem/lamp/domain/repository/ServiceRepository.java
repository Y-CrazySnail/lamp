package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.objvalue.Server;

import java.util.Date;
import java.util.List;

public interface ServiceRepository {


    Services getByUUID(String uuid);

    void save(Services services);

    void updateById(Services services);

    void removeById(Long id);

    List<Services> listByMemberId(Long memberId);



    IPage<Services> pages(int current, int size, Long memberId, String status, String wechat, String email);

    void updateUUID(Long memberId, Long serviceId, String uuid);


    List<Services> listService();

    List<Server> listServer();

    List<NodeVmess> listNodeVmess(Date currentDate);

    Services getServiceById(Long serviceId);

    Server getServerById(Long serverId);

    void saveNodeVmess(NodeVmess nodeVmess);

    void updateService(Services services);
}
