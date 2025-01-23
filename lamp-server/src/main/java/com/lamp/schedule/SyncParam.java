package com.lamp.schedule;

import com.lamp.entity.LampMember;
import com.lamp.entity.LampServer;
import lombok.Data;

@Data
public class SyncParam {
    private LampServer server;
    private LampMember member;
}
