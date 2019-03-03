package org.world.concurrencyinpractice.problem.b_object;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Vector;

/**
 * @Description 扩展Vector并添加一个“若没有则添加”方法
 * @DAuthor Double
 */
@ThreadSafe
public class BetterVector<E> extends Vector<E> {
	public synchronized boolean putIfAbsent(E x) {
		boolean absent = !contains(x);
		if (absent) {
			add(x);
		}
		return absent;
	}
}
