package org.world.demo.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * 安全地终止线程
 *
 * @Description 线程通过中断操作和cancel()方法均可是CountThread得以终止。
 * 这种通过标识位或者中断操作的方式能够是线程在终止时有机会去清理资源，
 * 而不是五段的将线程停止，
 * 因而这种终止线程的做法显得更加安全和优雅。
 * @Author Double
 */
public class Shutdown {

	public static void main(String[] args) throws InterruptedException {
		Runner one = new Runner();
		Thread countThread = new Thread(one, "CountThread");
		countThread.start();
		//睡眠 1 秒， main 线程对 CountThread 进行中断，使 CountThread 能够感知中断而结束
		TimeUnit.SECONDS.sleep(1);
		countThread.interrupt();
		Runner two = new Runner();
		countThread = new Thread(two, "CountThread");
		countThread.start();
		//睡眠 1 秒， main 线程对 Runner two 进行取消，使 CountThread 能够感知 on 为 false而结束
		TimeUnit.SECONDS.sleep(1);
		two.cancel();
	}

	private static class Runner implements Runnable {
		private long i;
		private volatile boolean on = true;

		@Override
		public void run() {
			while (on && !Thread.currentThread().isInterrupted()) {
				i++;
			}
			System.out.println("Count i = " + i);
		}

		public void cancel() {
			on = false;
		}
	}
}
