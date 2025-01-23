package com.lamp.schedule;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GlobalData {
    /**
     * 是否正在同步中
     */
    public static boolean SYNCING = false;
    /**
     * 同步队列
     */
    public static final BlockingQueue<SyncParam> SYNC_QUEUE = new LinkedBlockingQueue<>();
}
