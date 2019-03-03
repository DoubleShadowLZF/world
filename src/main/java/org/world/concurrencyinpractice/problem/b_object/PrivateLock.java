package org.world.concurrencyinpractice.problem.b_object;

import org.world.concurrencyinpractice.problem.a_base.Widget;

import javax.annotation.concurrent.GuardedBy;

/**
 * @Description 通过一个私有锁来保护状态
 * @DAuthor Double
 */
public class PrivateLock {
	private final Object myLock = new Object();

	@GuardedBy("myLock")
	Widget widget;

	void someMethod(){
		synchronized (myLock){
			//访问或修改widget的状态
		}
	}

}
