package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import com.yeem.lamp.domain.objvalue.Subscription;

import java.util.Date;
import java.util.List;

public interface ServiceRepository {

    Services getByUUID(String uuid);



    List<Services> listService(Services services);
    List<ServiceMonth> listServiceMonth(ServiceMonth serviceMonth);
    List<ServiceRecord> listServiceRecord(ServiceRecord serviceRecord, Date current);
    List<Subscription> listSubscription();




    IPage<Services> pages(int current, int size, Long memberId, String status, String wechat, String email);

    void save(Services services);

    void updateById(Services services);

    void removeById(Long id);

    List<Services> listByMemberId(Long memberId);

    List<Server> listServer();

    Services getServiceById(Long serviceId);

    Server getServerById(Long serverId);

    void updateService(Services services);

    void batchSaveServiceMonth(List<ServiceMonth> serviceMonthList);
}
