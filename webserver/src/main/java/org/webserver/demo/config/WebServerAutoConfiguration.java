package org.webserver.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.webserver.demo.WebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableConfigurationProperties(WebServerConfiguration.class)
@Configuration
public class WebServerAutoConfiguration {

    @Autowired
    private WebServerConfiguration webServerConfiguration;

    /**
     * 创建一个 web服务器
     * @return
     * @throws IOException
     */
    @Bean
    public WebServer WebServer() throws IOException {
        ServerSocket socket = new ServerSocket(webServerConfiguration.getPort());
        ExecutorService exec = Executors.newFixedThreadPool(webServerConfiguration.getInitThreadCount());
        return new WebServer(socket,exec);
    }
}
