package com.demo.concurrencyinpractice.problem.g_dead_lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Function 简单的锁顺序死锁（不要这么做）
 * @author Double
 */
@Slf4j
public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight(){
        synchronized (left){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (right){
                doSomething();
            }
        }
    }

    public void rightLeft(){
        synchronized (right){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (left){
                doSomething();
            }
        }
    }

    private void doSomething(){
        log.info("{}",java.lang.Thread.currentThread().getName());
    }


    public static void main(String[] args) {
        LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();
        ExecutorService service = Executors.newFixedThreadPool(2);
        Thread thread1 = new Thread("left thread") {
            @Override
            public void run() {
                leftRightDeadLock.leftRight();
            }
        };
        thread1.setName("left");

        Thread thread2 = new Thread("right thread") {
            @Override
            public void run() {
                leftRightDeadLock.rightLeft();
            }
        };
        thread1.setName("right");

        service.execute(thread1);
        service.execute(thread2);
        log.info("成功退出");
    }
}
