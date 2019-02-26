package org.world.demo.concurrency;

import org.world.demo.utils.SleepUtils;

/**
 * Daemon 线程
 *
 * @Description Daemon线程是一种支持型线程，因为它主要被用作程序中后台调度以及支持性工作。
 * 这意味着，当一个 Java 虚拟机中不存在非 Daemon 线程的时候，Java虚拟机将会退出。
 *
 */
public class Daemon {
	public static void main(String[] args) {
		System.out.println("DeamonRunner running...");
		Thread thread = new Thread(new DaemonRunner(), "DeamonRunner");
		//需要在运行前标记，不能动态设置标记
		thread.setDaemon(true);
		thread.start();
		System.out.println("DaemonThread running...");
		Thread daemonThread =new DaemonThread();
		daemonThread.setDaemon(true);
		daemonThread.start();
	}

	static class DaemonRunner implements Runnable {
//		Thread subThread = new SubThread();

		@Override
		public void run() {
			try {
				/*Thread subThread = new Thread("subThread");
				subThread.start();
				System.out.println(this.getClass().getName()+"的子线程"+subThread.getName()+"："+subThread.isDaemon());*/
				SleepUtils.second(10);
			} finally {
				System.out.println("DaemonThread finally run.");
			}
		}
	}

	static class DaemonThread extends Thread {
//		Thread subThread = new SubThread();

		@Override
		public void run() {
			try {
				Thread subThread = new Thread("subThread");
				subThread.start();
				System.out.println(this.getClass().getName()+"的子线程"+subThread.getName()+"："+subThread.isDaemon());
//				SleepUtils.second(10);
			} finally {
				System.out.println("DaemonThread finally run.");
			}
		}
	}

	static class SubThread extends Thread{
		@Override
		public void run(){
			System.out.println("SubThread....");
		}
	}
}
