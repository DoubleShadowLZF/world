package org.world.demo.concurrency;

import org.world.demo.utils.SleepUtils;
import org.world.demo.utils.TimeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 过期的suspend(),resume()和stop()
 *
 * @Description PrintThread 运行3秒，随后被暂停，3秒后恢复，最后经过3秒被终止。
 */
public class Deprecated {
	public static void main(String[] args) throws InterruptedException {
		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		Thread printThread = new Thread(new Runner(), "PrintThread");
		printThread.setDaemon(true);
		printThread.start();
		TimeUnit.SECONDS.sleep(3);
		//将printThread 进行暂停，输出内容工作停止
		//线程不会释放占有的资源（比如锁），而是占有着资源进入睡眠状态，这样容易引发死锁问题
		printThread.suspend();
		System.out.println("main suspend PrintThread at " + format.format(new Date()));
		TimeUnit.SECONDS.sleep(3);
		//将 PrintThread 进行恢复，输出内容继续
		printThread.resume();
		System.out.println("main resume PrintThread at " + format.format(new Date()));
		TimeUnit.SECONDS.sleep(3);
		//将 PrintThread 进行终止，输出内容停止
		//在终结一个线程时不会保证线程的资源正常释放，通常是没有给予线程完成资源释放工作的机会，
		//因此会导致程序可能工作在不确定状态下
		printThread.stop();
		System.out.println("main stop PrintThread at " + format.format(new Date()));
		TimeUnit.SECONDS.sleep(3);
	}

	static class Runner implements Runnable {

		@Override
		public void run() {
			DateFormat format = new SimpleDateFormat("HH:mm:ss");
			while (true) {
				System.out.println(Thread.currentThread().getName() + " run at " + format.format(new Date()));
				SleepUtils.second(1);
			}
		}
	}
}
