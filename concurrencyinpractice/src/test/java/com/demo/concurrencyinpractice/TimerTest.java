package com.demo.concurrencyinpractice;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TimerTest {


    /**
     * Timer在执行所有定时任务时只会创建一个线程。
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        long begin = System.currentTimeMillis();
        Timer timer = new Timer();
        log.info(">>>使用Timer执行定时任务<<<");
        log.info(">>>>开始计时");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info(">>>>第一个定时任务执行：{},并睡眠一秒", System.currentTimeMillis() - begin);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }, 0, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info(">>>第二个定时任务执行{}，并休息一秒", System.currentTimeMillis() - begin);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }, 0, 1000);
        Thread.sleep(3000);

        log.info("三秒后... {}", System.currentTimeMillis() - begin);
    }

    /**
     * 使用ScheduleThreadPoolExecutor 创建的任务时并行执行的。
     * @throws InterruptedException
     */
    @Test
    public void scheduledThreadPoolExecutorTest() throws InterruptedException {
        int count = 0;
        int threadPoolSize = 5;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(threadPoolSize);

        log.info(">>>使用ScheduleThreadPoolExecutor执行定时任务<<<");
        long begin = System.currentTimeMillis();
        log.info(">>>开始计时：{}",begin);
        service.schedule(() -> {
            try {
                log.info("第一个线程开始执行，{}，并睡眠一秒", System.currentTimeMillis() - begin);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }, 1, TimeUnit.SECONDS);
        service.schedule(()-> {
            try {
                log.info("第二个线程开始执行，{}，并睡眠一秒", System.currentTimeMillis() - begin);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        },1, TimeUnit.SECONDS);
        Thread.sleep(3000);
        log.info("三秒后... {}", System.currentTimeMillis() - begin);
    }
}
