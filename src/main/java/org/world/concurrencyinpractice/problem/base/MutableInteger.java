package org.world.concurrencyinpractice.problem.base;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;

/**
 * @Description 非线程安全的可变整数类
 */
@NotThreadSafe
public class MutableInteger {
	private int value;

	public int get() {
		return value;
	}

	public void set(int value) {
		this.value = value;
	}
}

/**
 * @Description 线程安全的可变整数类
 */
@ThreadSafe
class SynchronizedInteger {
	@GuardedBy("this")
	private int value;

	public synchronized int get() {
		return value;
	}
	public synchronized void set(int value){
		this.value = value;
	}
}
