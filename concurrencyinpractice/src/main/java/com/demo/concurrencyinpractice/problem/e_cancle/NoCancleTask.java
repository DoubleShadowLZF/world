package com.demo.concurrencyinpractice.problem.e_cancle;

import javafx.concurrent.Task;

import java.util.concurrent.BlockingQueue;

/**
 * 不可取消的任务在退出前恢复中断
 * <p>
 *     使用中断是实现取消最合理方式
 * </p>
 */
public class NoCancleTask {
    public Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try{
            while(true){
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                    //重新尝试
                }
            }
        }finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
