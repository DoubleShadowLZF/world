package com.demo.art;

import lombok.extern.slf4j.Slf4j;

/**
 * @Function 线程等待
 * @author Double
 */
@Slf4j
public class Join {
    public static void main(String[] args) {

        Thread pre = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Demino(pre),String.valueOf(i));
            thread.start();
            pre = thread;
        }
    }

    static class Demino implements Runnable{

        private Thread thread;

        public Demino(Thread thread){
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {

            }
            log.info("{} terminate.",Thread.currentThread().getName());
        }
    }
}
