package com.demo.concurrencyinpractice.problem.d_task;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * 支持关闭操作的web服务器
 */
@Slf4j
public class LifecleWebServer {
    private final ExecutorService exec = Executors.newCachedThreadPool();

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while(!exec.isShutdown()){
            try{
                final Socket conn = socket.accept();
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(conn);
                    }
                });
            }catch (RejectedExecutionException e){
                if(exec.isShutdown()){
                    log.debug("task submission rejected:{}",e);
                }
            }
        }
    }

    private void handleRequest(Socket conn) {
    }
}
