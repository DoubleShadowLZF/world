package org.webserver.demo;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettySocketServer {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(80);
        SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener((client) -> {
            //添加客户端连接监听器
            log.info("{} web客户端接入", client.getRemoteAddress());
            client.sendEvent("helloPush", "hello");
        });
//        server.addEventListener("helloEvent",Hell);

    }
}
