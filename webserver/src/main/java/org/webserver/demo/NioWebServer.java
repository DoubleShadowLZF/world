package org.webserver.demo;

import lombok.extern.slf4j.Slf4j;
import org.webserver.demo.task.ConsoleTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * web服务器
 * <p>使用nio实现socket</p>
 */
@Slf4j
public class NioWebServer {

    private Selector selector;

    private Executor exec = Executors.newCachedThreadPool();

    private Integer port ;

    public NioWebServer(Integer port) {
        this.port = port;
    }

    private void doAccep(SelectionKey key) {
        ServerSocketChannel serverchannel = (ServerSocketChannel) key.channel();
        SocketChannel channel;
        try {
            //生成和客户端的通信的通道
            channel = serverchannel.accept();
            //设置非阻塞模式
            channel.configureBlocking(false);
            //注册选择器，读就绪
            channel.register(selector, SelectionKey.OP_READ);
            InetAddress clientAddress = channel.socket().getInetAddress();
            log.info(">>>客户端{}连接上web服务器", clientAddress.getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            int readBytes = channel.read(byteBuffer);
            if (readBytes > 0) {
                exec.execute(new ConsoleTask(byteBuffer, key, selector));
            }
        } catch (IOException e) {
            //请求取消此键的通道到其选择器的注册
            key.cancel();
            if (key.channel() != null) {
                key.channel().close();
            }
            e.printStackTrace();
        }
    }

    private void doWrite(SelectionKey key,String text)throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try{
            byteBuffer.put(text.getBytes());
            //翻转这个缓冲区。 该限制设置为当前位置，然后将该位置设置为零。 如果标记被定义，则它被丢弃。
            //在通道读取或放置操作的序列之后，调用此方法来准备一系列通道写入或相对获取操作。
            byteBuffer.flip();
            channel.write(byteBuffer);
        }catch (Exception e){
            key.channel();
            if(key.channel() != null){
                key.channel().close();
            }
            e.printStackTrace();
        }
        //将key的interest集合设置为读模式
        key.interestOps(SelectionKey.OP_READ);
    }

    public void startServer() throws IOException{
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //设置为非非阻塞模式
        ssc.configureBlocking(false);

        InetSocketAddress isa = new InetSocketAddress(80);
        ssc.socket().bind(isa);

        //让Selector为这个channel服务，
        // 接收连接事件，表示服务器监听到客户端连接，
        //ServerSocketChannel只有OP_ACCEPT可用，
        // OP_CONNECT，OP_READ，OP_WRITE用于SocketChannel
        ssc.register(selector,SelectionKey.OP_ACCEPT);

        while(true){
            //阻塞方法
            selector.select();
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            while(it.hasNext()){
                SelectionKey key = (SelectionKey) it.next();
                //避免重复处理相同的SelectionKey
                it.remove();
                //测试此键的通道是否已准备好接受新的套接字连接（Socket连接）
                if(key.isAcceptable()){
                    doAccep(key);
                //此键是否有效 && 此键的通道是否准备好进行读取
                }else if(key.isValid() && key.isReadable()){
                    doRead(key);
                //此键是否有效 && 此键的通道是否已准备好进行写入
                }else if(key.isValid() && key.isWritable()){
                    doWrite(key,"web server ...");
                }

            }
        }
    }
}
