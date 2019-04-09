package org.webserver.demo.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "web-server.file-server")
@Data
public class FileServerConfiguration {
    /**
     * 文件服务器的存储路径
     */
    private String path;
}
