package org.webserver.demo.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.webserver.demo.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * 数据缓冲区
 */
@Slf4j
public class DataBuffer {
    private static Integer DEFAULT_SIZE = 1024;
    private static SelectionKey key;
    private static Selector selector;

    private static ByteBuffer writeBuffer = ByteBuffer.allocate(DEFAULT_SIZE);
    private static ByteBuffer readBuffer = ByteBuffer.allocate(DEFAULT_SIZE);

    /**
     * 初始化
     * @param pKey
     * @param pSelector
     */
    public static void init(SelectionKey pKey,Selector pSelector){
        key = pKey;
        selector = pSelector;
    }

    /**
     *
     * @param text
     */
    public static void writeAppend(String text) throws UnsupportedEncodingException {
        assert key != null && selector != null;

        log.debug("writeBuffer:{}",text);
        //①清除buffer数据
        writeBuffer.clear();
//        text = new String(text.getBytes("utf-8"),"utf-8");
//        text = URLEncoder.encode(text,"GBK");
//        text = StringUtil.utf82gbk(text);
        byte[] bytes = text.getBytes();
        if(bytes.length > DEFAULT_SIZE){
            writeBuffer = ByteBuffer.allocate(bytes.length);
        }
        //②放置数据
        writeBuffer.put(text.getBytes());
        //③重置偏移量
        writeBuffer.flip();

        //将key的interest集合设置为写模式
        key.interestOps(SelectionKey.OP_WRITE);
        //强制selector返回，使尚未返回的第一个选择操作立即返回，即取消selector.select
        selector.wakeup();
    }

    public static ByteBuffer writeBuffer(){
        return writeBuffer;
    }

}
