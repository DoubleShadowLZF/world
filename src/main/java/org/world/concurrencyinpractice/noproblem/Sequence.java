package org.world.concurrencyinpractice.noproblem;

import org.world.demo.utils.ThreadPoolUtil;

import javax.annotation.concurrent.GuardedBy;

/**
 * @Description
 */
public class Sequence implements Runnable{

	@GuardedBy("this")private int value;

	public synchronized int getNext(){
		return value++;
	}

	@Override
	public void run() {
		int next = getNext();
		System.out.println(Thread.currentThread().getName()+":"+next);
	}

	public static void main(String[] args) {
		Sequence sequence = new Sequence();
		ThreadPoolUtil.RunThread(sequence,100,1);
	}


}
