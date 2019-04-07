package com.demo.concurrencyinpractice.problem.f_thread_pool;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.CountDownLatch;

/**
 * 某个线程找到解答后，通知其他线程更新解答。
 * <p>
 *     一种闭锁（Latch）机制。具体说是一种包含结果的闭锁，构造一个阻塞的并且可携带结果的闭锁。
 * </p>
 * <p>
 *     每个人物首先查询solution闭锁，找到一个解答就停止。而在此之前，主线程需要等待，ValueLatch中的getValue将一直阻塞，
 *     直到有线程设置了这个值。ValueLatch提供了一种方式来保存这个值，只有第一次调用才会设置它。
 *     调用者能够判断这个值是否已经被设置，以及阻塞并等候它被设置。在第一次调用setValue时，将更新解答方案，并且CountDownLatch会递减，
 *     从getValue中释放主线程。
 * </p>
 * @param <T>
 */
@ThreadSafe
public class ValueLatch<T> {
    @GuardedBy("this")
    private T value = null;
    /**
     * 每当一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。
     */
    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet(){
        return done.getCount() == 0;
    }

    public synchronized void setValue(T newValue){
        if(!isSet()){
            value = newValue;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException{
        done.await();
        synchronized (this){
            return value;
        }
    }

}
