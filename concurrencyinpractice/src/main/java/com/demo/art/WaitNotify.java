package com.demo.art;

import com.demo.art.utils.Sleep;
import lombok.extern.slf4j.Slf4j;

/**
 * @Function 等待/通知机制
 * @author Double
 */
@Slf4j
public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) {
        Thread waitThread = new Thread(new WaitTask(),"waitTask");
        waitThread.start();
        Sleep.s(1);
        Thread notifyTask = new Thread(new NotifyTask(), "notifyTask");
        notifyTask.start();
    }

    static class WaitTask implements Runnable{

        @Override
        public void run() {
            //加锁，拥有 lock 的Monitor
            synchronized (lock){
                while(flag){
                    log.info("{} 获取锁", Thread.currentThread().getName());
                    try {
                        log.info("{} 线程等待，别的线程可以占有锁",Thread.currentThread().getName());
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    static class NotifyTask implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                log.info("{} 获取锁",Thread.currentThread().getName());
                lock.notify();
//                lock.notifyAll();
                flag = false;
                Sleep.s(2);
            }
            synchronized (lock){
                log.info("{} 再次获取锁",Thread.currentThread().getName());
                Sleep.s(2);
            }
        }
    }

}
