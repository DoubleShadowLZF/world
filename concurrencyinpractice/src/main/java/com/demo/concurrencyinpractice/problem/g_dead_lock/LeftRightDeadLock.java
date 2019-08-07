package com.demo.concurrencyinpractice.problem.g_dead_lock;

/**
 * @Function 简单的锁顺序死锁（不要这么做）
 * @author Double
 */
public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight(){
        synchronized (left){
            synchronized (right){
                doSomething();
            }
        }
    }

    public void rightLeft(){
        synchronized (right){
            synchronized (left){
                doSomething();
            }
        }
    }

    private void doSomething(){}


    public static void main(String[] args) {
        LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();
        new Thread(){
            @Override
            public void run(){
                leftRightDeadLock.leftRight();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                leftRightDeadLock.rightLeft();
            }
        }.start();

    }
}
