package com.demo.art;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Double
 * @Function 中断标记位
 */
@Slf4j
public class Interrupted {

    public static void main(String[] args) {
        Thread sleepThread = new Thread(new SleepThread(), "sleepThread");
        sleepThread.setDaemon(true);
        Thread busyThread = new Thread(new BusyThread(), "busyThread");
        busyThread.setDaemon(true);
        sleepThread.start(); //TIME_WAITING
        busyThread.start(); //RUNNABLE
        log.info("线程开始运行");
        log.info("线程开始中断");
        log.info("第一次中断");

        sleepThread.interrupt(); //TIME_WAITING
        busyThread.interrupt(); //RUNNABLE
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
        }

        log.info("{} 的中断标记位 ： {}", "sleepThread", sleepThread.isInterrupted());
        log.info("{} 的中断标记位 ： {}", "busyThread", busyThread.isInterrupted());

        log.info("第二次中断");
        sleepThread.interrupt(); //TIME_WAITING
        busyThread.interrupt(); //RUNNABLE
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }
        log.info("{} 的中断标记位 ： {}", "sleepThread", sleepThread.isInterrupted());
        log.info("{} 的中断标记位 ： {}", "busyThread", busyThread.isInterrupted());
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
        }

    }

    static class SleepThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    log.info("sleep thread is first sleeping.");
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("1.TimeUnit InterruptedException : {}", e.getMessage());
//                    return; //注意 main 方法中断该线程后，将会收到一个 中断异常，需要对此异常进行处理，不 return 的话，那么这个线程还是在 空循环，不会退出线程
                }

                try {
                    log.info("sleep thread is second sleeping.");
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("2.TimeUnit InterruptedException : {}", e.getMessage());
//                    return; //注意 main 方法中断该线程后，将会收到一个 中断异常，需要对此异常进行处理，不 return 的话，那么这个线程还是在 空循环，不会退出线程
                }

                try {
                    log.info("sleep thread is third sleeping.");
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("3.TimeUnit InterruptedException : {}", e.getMessage());
//                    return; //注意 main 方法中断该线程后，将会收到一个 中断异常，需要对此异常进行处理，不 return 的话，那么这个线程还是在 空循环，不会退出线程
                }

                try {
                    log.info("sleep thread is forth sleeping.");
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("4.TimeUnit InterruptedException : {}", e.getMessage());
//                    return; //注意 main 方法中断该线程后，将会收到一个 中断异常，需要对此异常进行处理，不 return 的话，那么这个线程还是在 空循环，不会退出线程
                }
            }
        }
    }

    static class BusyThread implements Runnable {

        @Override
        public void run() {
            while (true) {

            }
        }
    }
}
