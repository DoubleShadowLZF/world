package org.webserver.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.webserver.demo.NioWebServer;
import org.webserver.demo.WebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * web服务器自动配置类
 */
@Configuration
@EnableConfigurationProperties(WebServerConfiguration.class)
public class WebServerAutoConfiguration {

    @Autowired
    private WebServerConfiguration webServerConfiguration;

    /**
     * 创建一个 web服务器
     * @return
     * @throws IOException
     */
//    @Bean
    @Deprecated
    public WebServer WebServer() throws IOException {
        ServerSocket socket = new ServerSocket(webServerConfiguration.getPort());
        ExecutorService exec = Executors.newFixedThreadPool(webServerConfiguration.getInitThreadCount());
        return new WebServer(socket,exec);
    }

    /**
     * 创建一个 非阻塞的web服务器
     * @return
     */
    @Bean
    public NioWebServer NioWebServer(){
        return new NioWebServer(webServerConfiguration.getPort());
    }

}
