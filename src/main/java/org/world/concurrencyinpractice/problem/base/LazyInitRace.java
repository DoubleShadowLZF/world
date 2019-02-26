package org.world.concurrencyinpractice.problem.base;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * @Description 单例模式之懒汉
 */
@NotThreadSafe
public class LazyInitRace {

	public Object object = null;

	public Object getInstance() {
		//多线程环境下，可能多个线程并行执行到此处，将 object 对象创建了两次
		// “object == null”为多个线程之间的竞态条件
		if (object == null) {
			object = new Object();
		}
		return object;
	}
}
