package org.world.concurrencyinpractice.problem.a_base;

import javax.annotation.concurrent.ThreadSafe;

/**
 * @Description 重入
 * 如果内置锁是不是可重入的，那么这段代码将发生死锁
 * 内置锁 synchronized 是可重入的
 * 如果某个线程试图获得一个已经由它自己持有的锁，那么这个请求就会成功。
 */
@ThreadSafe
public class Widget {
	public synchronized void doSomething(){
		System.out.println("doSomething");
	}

	public class LoggingWidget extends Widget{
		@Override
		public synchronized void doSomething() {
			super.doSomething();
			System.out.println(toString()+":"+"calling doSomething..");
		}
	}


}
