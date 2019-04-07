package org.webserver.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * web服务器配置类
 */
@Configuration
@ConfigurationProperties(prefix = "web-server")
@Data
public class WebServerConfiguration {
    /**
     * web 服务器的对外访问端口号
     */
    private Integer port;
    /**
     * 线程池初始化大小
     */
    private Integer initThreadCount;
}
