package org.world.concurrencyinpractice.problem.c_module;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 闭锁应用场景：在即使测试中使用 CountDownLatch 来启动和停止线程
 * <p>
 *     创建一定数量的线程，利用它们并发地执行指定的任务。它使用两个闭锁，
 *     分别表示“起始门（Starting Gate）”和“结束门（Ending Gate）”。
 *     起始门计数器的初始值为1，而结束门计数器的初始值为工作线程的数量。
 *     每个工作线程首先要做的值就是在启动门上等待，从而确保所有线程都就绪后才开始执行。
 *     而每个线程要做的最后一件事情是将调用结束门的countDown方法减1，
 *     这能使主线程高效地等待直到所有工作线程都执行完成，因此可以统计所消耗的时间。
 * </p>
 */
public class TestHarness {

    public long timeTasks(int nThreads,final Runnable task){
        /**
         * 起始门，用来让全部线程全部启动
         */
        final CountDownLatch startGate = new CountDownLatch(1);
        /**
         * 结束门，nThreads 为启动的线程总数
         */
        final  CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        try{
                            startGate.await();
                        }finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
        startGate.countDown();
        endGate.countDown();
        long end = System.nanoTime();
        return end -start;
    }

    public static void main(String[] args) {
        TestHarness testHarness = new TestHarness();
        long runTime = testHarness.timeTasks(10,new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                Random r = new Random();
                try {
                    Thread.sleep(r.nextLong());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("RunTime:"+runTime);
    }
}
