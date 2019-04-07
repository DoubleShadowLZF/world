package com.demo.concurrencyinpractice.problem.e_cancle;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 通过改写interrupt 方法将非标准的取消操作封装在Thread中
 * <p>
 *     ReaderThread 管理了一个套接字连接，它采用同步方式从该套接字中读取数据，
 *     并将接收到的数据传递给 processBuffer。为了结束某个用户的连接或者关闭服务器，
 *     ReaderThread 改写了 interrupt 方法，使其既能处理标准的中断，也能关闭底层的套接字。
 *     因此，无论ReaderThread 线程是在 read 方法中阻塞还是某个可中断的阻塞方法中阻塞，
 *     都可以被中断并停止执行当前的工作。
 * </p>
 */
public class ReaderThread extends Thread {

    private final Socket socket;

    private final InputStream in;

    private final int BUFSZ = 512;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    public void interrupt(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            super.interrupt();
        }
    }

    public void run(){
        try {
            byte[] buf = new byte[BUFSZ];
            while(true){
                int count = in.read(buf);
                if(count < 0){
                    break;
                }else if(count > 0){
                    processBuffer(buf,count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processBuffer(byte[] buf, int count) {

    }
}
