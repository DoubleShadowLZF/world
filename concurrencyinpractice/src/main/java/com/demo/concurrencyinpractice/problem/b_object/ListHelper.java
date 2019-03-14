package com.demo.concurrencyinpractice.problem.b_object;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description 非线程安全的“若没有则添加”（不要这么做）
 * 即使获取到的list上所有链表操作都是线程安全的，putIfAbsent()也是用了synchronized关键字修饰，
 * 但是 putIfAbsent()方法被synchronized关键字修饰锁住的 ListHelper对象，
 * 而list内的链表操作是针对list对象，因此，即使加上了 synchronized 关键字修饰，该方法也不是线程安全的。
 * @DAuthor Double
 */
@NotThreadSafe
public class ListHelper<E> {
	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public synchronized boolean putIfAbsent(E x){
		boolean absent = !list.contains(x);
		if(absent){
			list.add(x);
		}
		return absent;
	}
}

class ThreadSafeListHelper<E>{
	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public  boolean putIfAbsent(E x){
		synchronized(list){
			boolean absent = !list.contains(x);
			if(absent){
				list.add(x);
			}
			return absent;
		}
	}
}

