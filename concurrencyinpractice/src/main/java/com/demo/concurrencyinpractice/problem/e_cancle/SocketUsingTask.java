package com.demo.concurrencyinpractice.problem.e_cancle;

import lombok.Setter;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 通过 newTaskFor 将非标准的取消操作封装在一个任务中
 * <p>
 *     SocketUsingTask 实现了 CancellableTask ，并定义了Future.cancel 来关闭套接字和调用
 *     super.cancel。如果SocketUsingTask 通过自己的 Future 来取消，那么底层的套接字将关闭并且线程被中断。
 *     因此它提高了任务对取消操作的响应性：
 *     不仅能够调用可中断方法的同时确保响应取消操作，而且还能调用可阻调的套接字I/O方法。
 * </p>
 * @param <T>
 */
public abstract class SocketUsingTask<T> implements CancellableTask<T> {
    @GuardedBy("this")
    @Setter
    private Socket socket;

    public synchronized void cancel(){
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public RunnableFuture<T> newTask(){
        return new FutureTask<T>(this){
            public boolean cancel(boolean mayInterruptIfRunning){
                try {
                    SocketUsingTask.this.cancel();
                }finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}

interface CancellableTask<T> extends Callable<T> {
    void cancle();

    RunnableFuture<T> newTask();
}

@ThreadSafe
class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                              RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask) {
            return ((CancellableTask<T>) callable).newTask();
        } else {
            return super.newTaskFor(callable);
        }
    }


}


