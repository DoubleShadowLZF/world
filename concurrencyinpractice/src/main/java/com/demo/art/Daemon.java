package com.demo.art;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Function daemon 线程用作程序中后台调度以及支持性工作。
 * @author Double
 */
@Slf4j
public class Daemon {
    public static void main(String[] args) {
        DaemonTask daemonTask = new DaemonTask();
        Thread thread = new Thread(daemonTask, "daemonTask");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonTask implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                log.info("daemonTask退出", "");
            }
        }
    }
}
