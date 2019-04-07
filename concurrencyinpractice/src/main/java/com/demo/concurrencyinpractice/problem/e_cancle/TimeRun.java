package com.demo.concurrencyinpractice.problem.e_cancle;

import org.world.util.ExceptionUtil;

import java.util.concurrent.*;

/**
 *
 */
public class TimeRun {

    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

    /**
     * 在外部线程中安排中断（不要这么做）
     * <p>
     *     如果任务在超时之前完成，那么中断timeRun所在线程的取消任务将在timeRun返回到调用者之后启动。
     *     我们不知道在这种情况下将运行什么代码，但结果一定是不好的。
     * </p>
     * @param r
     * @param timeout
     * @param unit
     */
    public static void timeRun1(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);
        r.run();
    }

    /**
     * 在专门的线程中中断任务
     * <p>
     * 执行任务的线程拥有自己的执行策略，即使任务不响应中断，限时运行的方法仍能返回到它的调用者。
     * 在启动任务线程之后，timeRun 将执行一个限时的join方法。在join返回后，它将检查任务是否有异常跑出，
     * 如果有的话，则会在调用timeRun的线程中再次抛出该异常。由于Throwable在两个线程之间共享，
     * 因此该变量被声明为volatile 类型，从而确保安全地将其从任务线程发布到timeRun 线程。
     * </p>
     *
     * @param r
     * @param timeout
     * @param unit
     * @throws InterruptedException
     */
    public static void timeRun2(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RetthrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Exception e) {
                    e.printStackTrace();
                    this.t = e;
                }
            }

            void rethrow() {
                if (t != null) {
                    throw ExceptionUtil.launderThrowable(t);
                }
            }
        }
        RetthrowableTask task = new RetthrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(() -> {
            taskThread.interrupt();
        }, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }

    /**
     * 通过 Future 来取消任务
     * <p>
     *     将任务提交给一个 ExecutorService ，并通过一个定时的Future.get 来获取结果。
     *     如果get在返回时，抛出了一个TimeoutException ，那么任务将通过它的 Future 来取消。
     *     （为了简化代码，这个版本的timeRun在finally块中将直接调用Future.cancel，因为取消一个已完成的任务不会带来任何影响。）
     *     如果任务在被取消前就抛出一个异常，那么该异常将重新抛出以便调用者来处理异常。
     *     在程序清单中一种良好的编程习惯：取消不再需要结果的任务。
     * </p>
     * @param r
     * @param timeout
     * @param unit
     * @throws InterruptedException
     */
    public static void timeRun(Runnable r,long timeout,TimeUnit unit)throws InterruptedException{
        Future task =cancelExec.submit(r);
        try {
            task.get(timeout,unit);
        } catch (ExecutionException e) {
            e.printStackTrace();
            //如果在任务中抛出异常，name重新抛出该异常
            throw  ExceptionUtil.launderThrowable(e);
        } catch (TimeoutException e) {
            e.printStackTrace();
            //接下来任务将被取消
        }finally {
            //如果任务已经结束，那么执行取消操作也不会带来任何影响
//            System.out.println("任务取消");
            task.cancel(true);//如果任务正在运行，那么将被中断
        }
    }

    public static void main(String[] args) throws InterruptedException {
        timeRun(() ->{
                System.out.println("hello world");
                throw new RuntimeException();
        },1000,TimeUnit.MICROSECONDS);
    }
}
