package com.demo.concurrencyinpractice.problem.d_task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 串行的Web服务器
 * <p>
 *     最简单的策略就是在单个线程中串行地执行各项任务。程序 SingleThreadWebServer 将串行地处理它的任务（通过 80 端口接收到的HTTP请求）。
 * </p>
 * <p>
 *     SingleThreadWebServer 很简单，且在理论上是正确的，但在实际生产环境中的执行性能却很糟糕，因为它每次只能处理一个请求。
 *     主线程在接收连接与处理相关请求等操作之间不断地将再次调用accept。如果处理请求的速度很快并且handleRequest可以立即返回，
 *     那么这种方法是可行的，但现实世界中的Web服务器的情况并非如此。
 * </p>
 */
public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while(true){
            Socket connerction = socket.accept();
            handleRequest(connerction);
        }
    }

    private static void handleRequest(Socket connerction) {
    }
}
