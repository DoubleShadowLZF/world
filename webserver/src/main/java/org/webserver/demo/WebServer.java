package org.webserver.demo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.webserver.demo.task.RequestTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

/**
 * web 服务器
 * <p>使用传统I/O实现socket</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class WebServer {

    /**
     * socket连接
     */
    private ServerSocket socket = null;
    /**
     * socket连接池，并发处理多个连接
     */
    private Executor exec = null;

    public void doService() throws IOException {
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new RequestTask(connection);
            exec.execute(task);
        }
    }


}
