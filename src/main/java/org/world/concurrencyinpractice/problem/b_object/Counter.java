package org.world.concurrencyinpractice.problem.b_object;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

/**
 * @Description 使用Java监视器模式的线程安全计数器
 * @DAuthor Double
 */
@ThreadSafe
public class Counter {
	@GuardedBy("this")
	private long value = 0;

	public synchronized long getValue() {
		return value;
	}

	public synchronized long increment(){
		if(value == Long.MAX_VALUE){
			throw new IllegalStateException("count overflow");
		}
		return ++value;
	}
}
