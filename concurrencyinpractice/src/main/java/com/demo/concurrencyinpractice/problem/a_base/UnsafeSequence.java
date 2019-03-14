package com.demo.concurrencyinpractice.problem.a_base;

import org.world.demo.utils.ThreadPoolUtil;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * @Description 非线程安全的数值序列生成器
 * @Author Double
 */
@NotThreadSafe
public class UnsafeSequence implements Runnable{
	private static int value;

	public static int getNext(){
		return value++;
	}

	@Override
	public void run() {
		int next = UnsafeSequence.getNext();
		System.out.println(Thread.currentThread().getName()+":"+next);
	}

	public static void main(String[] args) {
		UnsafeSequence unsafeSequence = new UnsafeSequence();
		ThreadPoolUtil.RunThread(unsafeSequence,10000,1);


//		UnsafeSequence unsafeSequence = new UnsafeSequence();

		//新建一个ThreadPoolExecutor线程池对象
//		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

		//循环并发多个线程
/*
		for (int i = 0; i < 10000; i++) {
			//ThreadPoolExecutor的execute方法
			CountRunner countRunner = new CountRunner();
			executor.execute(countRunner);
		}
*/

		//多线程任务执行完毕，关闭线程池
//		executor.shutdown();
	}
}
