package org.springboot.distribute.queue;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Redis 实现队列
 * push的数据只支持 List 类型
 */
@Component
public class RedisQueueBak<T>  implements InitializingBean, DisposableBean {
    @Autowired
    private RedisTemplate redisTemplate;

    private String key = "REDIS_QUEUE";
    private byte[] rawKey;
    private RedisConnectionFactory factory;

    private RedisConnection connection; // for blocking
    private BoundListOperations<String,T> listOperations; // noblocking

    private Lock lock = new ReentrantLock(); // 基于底层IO阻塞考虑

    private RedisQueueListener listener;//异步回调
    private Thread listenerThread;

    private boolean isClosed;

    /**
     * blocking
     * remove and get list item from queue:BRPOP
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    public T takeFromTail(int timeout) throws InterruptedException{
        lock.lockInterruptibly();
        try{
            return (T)connection.bRPop(timeout,rawKey);
        }finally {
            lock.unlock();
        }
    }

    public T takeFromTail() throws InterruptedException {
        return takeFromTail(0);
    }

    /**
     * insert data into the header of queue
     * @param value
     */
    public void pushFromHead(T value){
        listOperations.leftPush(value);
    }

    public void pushFromTail(T value){
        listOperations.rightPush(value);
    }

    public T removeFromHead(){
        return listOperations.rightPop();
    }

    public T removeFromTail(){
        return listOperations.rightPop();
    }

    public T takeFromHead(int timeout) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            List<byte[]> bytes = connection.bLPop(timeout, rawKey);
            if(CollectionUtils.isEmpty(bytes)){
                return null;
            }
            return (T)redisTemplate.getValueSerializer().deserialize(bytes.get(1));
        }finally {
            lock.unlock();
        }
    }

    public T takeFromHead() throws InterruptedException {
        return takeFromHead(0);
    }

    private void shutdown(){
        try {
            listenerThread.interrupt();
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void destroy() throws Exception {
        if(isClosed){
            return ;
        }
        shutdown();
        RedisConnectionUtils.releaseConnection(connection,factory);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        factory = redisTemplate.getConnectionFactory();
        connection = RedisConnectionUtils.getConnection(factory);
        rawKey = redisTemplate.getKeySerializer().serialize(key);
        listOperations = redisTemplate.boundListOps(key);
        if(listener != null){
            listenerThread = new ListenerThread();
            listenerThread.setDaemon(true);
            listenerThread.start();
        }
    }

    class ListenerThread extends Thread{
        @Override
        public void run() {
            try{
                while(true){
                    T value = takeFromHead(); //cast exception ,you should check
                    //逐个执行
                    if(value != null){
                        try{
                            listener.onMessage(value);
                        }catch (Exception e){

                        }
                    }
                }
            }catch(InterruptedException e){

            }
        }
    }
}
