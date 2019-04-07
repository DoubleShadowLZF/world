package org.webserver.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Spring容器加载完毕后，启动web服务器
 */
@Component
public class WebServerRunner implements ApplicationRunner {

    @Autowired
    private NioWebServer webServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        webServer.startServer();
    }
}
