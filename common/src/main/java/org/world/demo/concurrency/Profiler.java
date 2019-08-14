package org.world.demo.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal的使用
 * @Description ThreadLocal，即线程变量，是一个以ThreadLocal对象为键、任意对象为值的存储结构。这个结构被附带在线程上，
 * 也就是说一个线程可以根据一个 ThreadLocal 对象查询到绑定在这个线程上的一个值。
 * <p>
 * 构建一个常用的Profiler类，它具有begin()和end()两个方法，
 * 而end()方法返回从begin()方法调用开始到end()方法被调用时的时间差，单位是毫秒。
 * </p>
 */
public class Profiler {

	/**
	 * 第一次get()方法调用时会进行初始化(如果set方法没有调用)，每个线程会调用一次 	
	 */
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
		protected Long initValue(){
			return System.currentTimeMillis();
		}
	};

	public static final void begin(){
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}

	public static final long end(){
		return System.currentTimeMillis() - TIME_THREADLOCAL.get();
	}

	public static void main(String[] args) throws InterruptedException {
		Profiler.begin();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Cost:" + Profiler.end() + " mills");
	}
}
