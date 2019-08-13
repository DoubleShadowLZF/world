package com.demo.art;

import com.demo.art.utils.Sleep;
import lombok.extern.slf4j.Slf4j;

/**
 * @Function 安全地终止线程
 * @author Double
 */
@Slf4j
public class Shutdown {

    public static void main(String[] args) {
        Runner runner = new Runner();
        Thread countThread = new Thread(runner, "shutdownThread");
        countThread.start();
        Sleep.s(1);
//        runner.cancel();
        countThread.interrupt();
        Runner runner1 = new Runner();
        Thread shutdown2Thread = new Thread(runner1, "shutdown2Thread");
        shutdown2Thread.start();
        Sleep.s(1);
        runner1.cancel();
    }

    private static class Runner implements Runnable{

        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while ( on && ! Thread.currentThread().isInterrupted()){
                i++;
            }
            log.info("i:{}",i);
        }

        public void cancel(){
            log.info("{}线程终止",Thread.currentThread().getName());
            on = false;
        }
    }
}
