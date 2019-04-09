package org.webserver.demo.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * 非阻塞的客户端
 */
@Slf4j
public class NioClient {
    private Selector selector;

    public void init(String ip,int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip,port));
        //连接就绪，表示客户与服务器的连接已经成功建立
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void connect(SelectionKey key) throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();
        try{
            //如果正在连接，则完成连接
            if(channel.isConnectionPending()){
                //完成套接字通道的连接过程
                channel.finishConnect();
            }
            channel.configureBlocking(false);
            channel.write(ByteBuffer.wrap(new String("list").getBytes()));
            //注册选择器，读就绪
            channel.register(this.selector,SelectionKey.OP_READ);
        }catch (IOException e){
            channel.close();
            key.selector().close();
            e.printStackTrace();
        }
    }

    public void read(SelectionKey key)throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();
        //创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        try{
            channel.read(buffer);
            byte[] data = buffer.array();
            String msg = new String(data).trim();
            log.info(">>>client:{}",msg);
        }catch (IOException e){
            channel.close();
            key.selector().close();
        }
    }

    public void working()throws IOException{
        while(true){
            if(!selector.isOpen()){
                break;
            }
            selector.select();
            Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                //连接事件发生
                if(key.isConnectable()){
                    connect(key);
                }else if(key.isReadable()){
                    read(key);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        NioClient client = new NioClient();
        client.init("127.0.0.1",80);
        client.working();
    }
}
