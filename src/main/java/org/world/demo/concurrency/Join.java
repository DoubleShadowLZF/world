package org.world.demo.concurrency;

/**
 * Thread.join()的使用
 *
 * @Description 线程A 执行了thread.join()含义：
 * 当前线程A等待thread线程终止之后才从thread.join()返回。
 * <p>
 * 功能：创建10个编程，编号0~9，每个线程调用前一个线程的join()方法，
 * 也就是线程0结束了，线程1才能从join()方法中返回，而线程0需要等待main线程结束。
 * </p>
 */
public class Join {
	public static void main(String[] args) {

		Thread previous = Thread.currentThread();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Domino(previous), String.valueOf(i));
			thread.start();
			previous = thread;
		}
	}

	static class Domino implements Runnable {
		private Thread thread;

		public Domino(Thread thread) {
			this.thread = thread;
		}

		@Override
		public void run() {
			try {
				thread.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " terminate.");
		}
	}
}
/*
	0 terminate.
	1 terminate.
	2 terminate.
	3 terminate.
	4 terminate.
	5 terminate.
	6 terminate.
	7 terminate.
	8 terminate.
	9 terminate.
	每个线程终止的前提是前驱线程的终止，每个线程等待前驱线程终止 后，才能从join()方法返回，
	这里涉及了等待/通知机制（等待前驱线程结束，接收前驱线程结束通知）。
 */
