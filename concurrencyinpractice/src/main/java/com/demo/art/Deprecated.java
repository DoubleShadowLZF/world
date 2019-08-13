package com.demo.art;

import com.demo.art.utils.Sleep;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Double
 * @Function TODO
 */
@Slf4j
public class Deprecated {
    public static void main(String[] args) {
        Runner printer = new Runner();
        Thread pThread = new Thread(printer,"printer-01");
        Thread pTempThread = new Thread(printer,"printer-01");

        int sleepSeconds = 60;

        log.info("Printer is started at {}", time());
        pThread.start();
        Sleep.s(2);

        log.info("Printer is suspend at {}", time());
        pThread.suspend();

        log.info("Printer template is start and suspend at {}",time());
        pTempThread.start();
        pTempThread.suspend();
        Sleep.s(sleepSeconds);

        log.info("Printer is resume at {}", time());
        pThread.resume();
        Sleep.s(sleepSeconds);

        log.info("Printer is stop at {}", time());
        pThread.stop();
        Sleep.s(sleepSeconds);
    }

    public static String time() {
        return new DateTime().toString("HH:mm:ss");
    }
 static Lock lock = new ReentrantLock();
    static class Runner implements Runnable {

        @Override
        public void run() {
            while (true) {
                log.info("{} printer is locked.",Thread.currentThread().getName());
                lock.lock();
                log.info("{} at {}", Thread.currentThread().getName(), new DateTime().toString("HH:mm:ss"));
                Sleep.s(30);
                log.info("{} printer is unlocked.",Thread.currentThread().getName());
                lock.unlock();
            }
        }
    }
}
