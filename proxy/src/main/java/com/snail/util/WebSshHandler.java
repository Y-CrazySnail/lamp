package com.snail.util;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.snail.chinaybop.entity.Server;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/ssh/{serverId}")
public class WebSshHandler {
    /**
     * 通道类型
     */
    private final static String channelType = "shell";
    /**
     * 列数
     */
    private final static Integer col = 83;
    /**
     * 行数
     */
    private final static Integer row = 30;
    /**
     * 宽度
     */
    private final static Integer wp = 80 * 8;
    /**
     * 高度
     */
    private final static Integer hp = 30 * 16;
    /**
     * Websocket缓存
     */
    private static final CopyOnWriteArraySet<WebSshHandler> webSocketSet = new CopyOnWriteArraySet<>();
    /**
     * PC信息M缓存
     */
    public static Map<Long, Server> serverInfoMap = new ConcurrentHashMap<>();
    /**
     * jsch对象
     */
    private static final JSch jsch = new JSch();
    /**
     * jsch会话
     */
    private com.jcraft.jsch.Session jschSession;
    /**
     * jsch通道
     */
    private ChannelShell channel;
    /**
     * jsch输入流
     */
    private InputStream inputStream;
    /**
     * jsch输出流
     */
    private OutputStream outputStream;
    /**
     * 接收通道响应内容线程
     */
    private Thread thread;
    /**
     * PC客户端ID
     */
    private Long serverId;

    @OnOpen
    public void onOpen(final Session session, @PathParam(value = "serverId") Long serverId) throws JSchException, IOException {
        webSocketSet.add(this);
        this.serverId = serverId;
        Server server = serverInfoMap.get(serverId);
        jschSession = jsch.getSession(server.getUsername(), server.getIp(), server.getPort());
        jschSession.setPassword(server.getPassword());
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.connect();
        channel = (ChannelShell) jschSession.openChannel(channelType);
        channel.setPtySize(col, row, wp, hp);
        inputStream = channel.getInputStream();
        outputStream = channel.getOutputStream();
        channel.connect();

        // 读取返回信息时会阻塞，启用线程读取channel返回内容
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    byte[] byteSet = new byte[2048];
                    int res = inputStream.read(byteSet);
                    if (res == -1) {
                        res = 0;
                    }
                    while (session != null && session.isOpen()) {
                        ByteBuffer byteBuffer = ByteBuffer.wrap(byteSet, 0, res);
                        synchronized (this) {
                            if (res != 0) {
                                // 响应至前端
                                session.getBasicRemote().sendBinary(byteBuffer);
                            }
                        }
                        res = inputStream.read(byteSet);
                        if (res == -1) {
                            res = 0;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    /**
     * Websocket连接关闭
     */
    @OnClose
    public void onClose() {
        // 移除对象
        webSocketSet.remove(this);
        // ssh通道断开连接
        channel.disconnect();
        // ssh会话断开连接
        jschSession.disconnect();
        // stockPcInfoMap
        serverInfoMap.remove(serverId);
    }

    /**
     * 接收客户端消息
     *
     * @param message 消息内容
     * @param session 会话
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        outputStream.write(message.getBytes());
        outputStream.flush();
    }
}
