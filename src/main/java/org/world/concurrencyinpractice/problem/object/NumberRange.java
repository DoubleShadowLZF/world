package org.world.concurrencyinpractice.problem.object;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 委托失效情况
 * 并不足以保护它的不变形条件（不要这么做）
 * 没有维持对下界和上界进行约束的不变形条件，没有足够的加锁机制来保证这些操作的原子性。
 * 假设取值范围为（0,10），如果一个线程调用setLower(5)，而另一个线程调用setUpper(4)，
 * 那么在一些错误的执行时序中，这两个调用都将通过检查，并且都能设置成功。
 * 得到的取值范围就是(5,4)
 *
 * @DAuthor Double
 */
public class NumberRange {
	/**
	 * 不变性条件：lower<=upper
	 */
	private final AtomicInteger lower = new AtomicInteger(0);
	private final AtomicInteger upper = new AtomicInteger(0);

	public void setLower(int i) {
		//注意：不安全的“先检查后执行”
		if (i > upper.get()) {
			throw new IllegalArgumentException("can't set lower to " + i + " > upper");
		}
		lower.set(i);
	}

	public void setUpper(int i){
		//注意：不安全的“先检查后执行”
		if(i<lower.get()){
			throw new IllegalArgumentException("can't set upper to " + i + " < lower");
		}
		upper.set(i);
	}

	public boolean isInRangee(int i){
		return (i >= lower.get() && i <= upper.get());
	}
}
