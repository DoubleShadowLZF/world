package com.demo.concurrencyinpractice.problem.d_task;

import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 错误的Timer行为
 * <p>
 *     运行1秒就结束了，并抛出了一个异常消息“Timer already cancelled”
 * </p>
 */
public class OutOfTime {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(),1);
        SECONDS.sleep(1);
        timer.schedule(new ThrowTask(),1);
        SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask{

        @Override
        public void run() {
            throw  new RuntimeException();
        }
    }
}
