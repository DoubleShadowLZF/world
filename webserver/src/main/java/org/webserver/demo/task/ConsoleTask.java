package org.webserver.demo.task;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * 控制台任务
 */
@Slf4j
public class ConsoleTask implements Runnable {

    private ByteBuffer byteBuffer;
    private SelectionKey key;
    private Selector selector;

    public ConsoleTask(ByteBuffer byteBuffer, SelectionKey key, Selector selector) {
        this.byteBuffer = byteBuffer;
        this.key = key;
        this.selector = selector;
    }

    @Override
    public void run() {
        //将key的interest集合设置为写模式
        key.interestOps(SelectionKey.OP_WRITE);
        //强制selector返回，使尚未返回的第一个选择操作立即返回，即取消selector.select
        selector.wakeup();
        byteBuffer.flip();
        String text = new String(byteBuffer.array(), 0, byteBuffer.limit());
        log.info(">>>" + text);
        //执行命令
        CmdTask cmdTask = new CmdTask(text);
        cmdTask.run();
    }
}
