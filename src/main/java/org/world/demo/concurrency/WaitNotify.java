package org.world.demo.concurrency;

import org.world.demo.utils.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 等待/通知机制
 *
 * @Description 等待/通知机制
 * <p>
 * 一个线程修改了一个对象的值，而另一个线程感知到了变化，
 * 然后进行相应的操作，整个过程开始于一个线程，而最终又执行又是另一个线程。
 * 前者是生产者，后者是消费者。
 * </p>
 * Java实现等待/通知机制：
 * <p>
 * 一个线程A调用了对象O的wait()犯法进入等待状态，而另一个线程B调用了对象O的notify()或者notifyAll()方法，
 * 线程A收到通知后从对象O的wait()方法返回，进而执行后续操作。
 * 上述两个线程通过对象O来完成交互，而对象上的wait()和notify/notifyAll()的关系就如同开关信息一样，
 * 用来完成等待方和通知方之间的交互工作。
 * </p>
 * @Author Double
 */
public class WaitNotify {
	static boolean flag = true;
	static Object lock = new Object();

	public static void main(String[] args) throws InterruptedException {
		Thread waitThread = new Thread(new Wait(), "WaitThread");
		waitThread.start();
		TimeUnit.SECONDS.sleep(1);
		Thread notifyThread = new Thread(new Notify(), "NotifyThread");
		notifyThread.start();
	}

	/**
	 * WaitThread 首先获取了对象的锁，然后调用对象的wait()方法，从而放弃了锁并进入了对象的等待队列WaitQueue中，进入等待状态。
	 * 由于WaitThread释放了对象的锁，NotifyThread 随后获取了对象的锁，并调用对象的notify()方法，
	 * 将WaitThread从WaitQueue移到SynchronizedQueue中，此时WaitThread的状态变为阻塞状态。
	 * NotifyThread释放了锁值后，WaitThread再次获取到锁并从wait()方法返回继续执行。
	 */
	static class Wait implements Runnable {
		@Override
		public void run() {
			//加锁，拥有lock的Monitor
			synchronized (lock) {
				//当条件不满足时，继续wait，同时释放了lock的锁
				while (flag) {
					try {
						System.out.println(Thread.currentThread() + " flag is true. wait@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
						lock.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//条件满足时，完成工作
				System.out.println(Thread.currentThread() + " flag is false .running@ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}
	}

	static class Notify implements Runnable {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

		@Override
		public void run() {
			//加锁，拥有lock的Monitor
			synchronized (lock) {
				//获取lock的锁，然后进行通知，通知时不会释放lock的锁，
				//直到当前线程释放了lock后，WaitThread才能从wait方法中返回
				System.out.println(Thread.currentThread() + " hold lock. notify @" + simpleDateFormat.format(new Date()));
				lock.notifyAll();
				flag = false;
				SleepUtils.second(5);
			}
			//再次加锁
			synchronized (lock) {
				System.out.println(String.format(Thread.currentThread() + " hold lock again. Sleep @ " + simpleDateFormat.format(new Date())));
				SleepUtils.second(5);
			}
		}
	}
}

/*
	等待/通知的经典范式
	分为等待方（消费者）和通知方（生产者）
	等待方遵循如下原则：
	1）获取对象的锁
	2）如果条件不满足，那么调用对象的wait()方法，被通知后仍要检查条件
	3）条件满足则执行对应的逻辑
	对应的伪代码如下：
	synchronized(对象){
		while(条件不满足){
			对象.wait();
		}
		对应的处理逻辑
	}
	通知方遵循如下原则：
	1）获取对象的锁
	2）改变条件
	3）通知所有等待在对象上的线程
	对应的伪代码如下：
	synchronized(对象){
		改变条件
		对象.notifyAll();
	}
 */
