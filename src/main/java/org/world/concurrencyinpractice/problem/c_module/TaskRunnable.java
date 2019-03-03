package org.world.concurrencyinpractice.problem.c_module;

import org.springframework.scheduling.config.Task;

import java.util.concurrent.BlockingQueue;

public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;

    /**
     *
     */
    public void run(){
        try {
            processTask();
        } catch (InterruptedException e) {
            //①传递异常
//            throw new InterruptedException(e.getMessage());
            //②恢复被中断的状态
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 抛出 InterruptException 则该方法是一个可阻塞的方法
     * @throws InterruptedException
     */
    private void processTask() throws InterruptedException{

    }
}
