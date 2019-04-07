package org.webserver.demo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.webserver.demo.config.WebServerConfiguration;
import org.webserver.demo.task.RequestTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * web 服务器
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class WebServer {

    private ServerSocket socket = null;
    private Executor exec = null;

    public void doService() throws IOException {
        while(true){
            final Socket connection = socket.accept();
            Runnable task = new RequestTask(connection);
            exec.execute(task);
        }
    }



}
