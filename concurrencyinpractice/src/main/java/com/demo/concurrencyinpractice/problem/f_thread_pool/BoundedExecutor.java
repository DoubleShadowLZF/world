package com.demo.concurrencyinpractice.problem.f_thread_pool;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * 使用 Semaphore 来控制任务的提交速率
 * <p>
 *     该方法使用了一个无界队列（因为不能限制队列的大小和任务的到达率），
 *     并设置信号量的上界设置为主线程的大小加上可排队任务的数量，
 *     这是因为信号量需要控制正在执行的和等待执行的任务数量。
 * </p>
 */
@ThreadSafe
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec , int bound){
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    }finally {
                        semaphore.release();
                    }
                }
            });

        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }

    /**
     * 代替方法：
     *  创建一个固定大小的线程池，并采用有界队列以及“调用者运行”饱和策略
     *
     * ThreadPoolExecutor executor =
     * 	new ThreadPoolExecutor(N_THREADS,N_THREADS,
     * 		0L, TimeUnit.MILLISECONDS,
     * 		new LinkedBlockingQueue<Runnable>(CAPACITY));
     * executor.setRejectedExecutionHandler(
     * 	new ThreadPoolExecutor.CallerRunsPolicy());
     *
     */

}
