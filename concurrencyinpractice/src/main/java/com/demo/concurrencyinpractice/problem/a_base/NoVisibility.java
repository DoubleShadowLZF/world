package com.demo.concurrencyinpractice.problem.a_base;

/**
 * @Description 在没有不同的情况下共享变量（不要这么做）
 */
public class NoVisibility {
	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {
		@Override
		public void run() {
			while (!ready) {
				//将CPU让给其他具有相同优先级的线程或自己继续运行，不一定会让其他相同优先级的线程执行
				Thread.yield();
				System.out.println(number);
			}
		}
	}

	public static void main(String[] args) {
		//①
		new ReaderThread().start();
		//②
		number = 42;
		//③
		ready = true;
	}
}
/*
 ①---②---③ //控制台无打印
      ①---②---③ //打印一次42
           ①---②---③ //此时控制台打印不断打印42

 ①---③---② //线程不退出，控制台也没有打印
      ①---③---② //控制台不断打印42
           ①---③---② //控制台不断打印42

   同时，编译器重排序还有可能是①---②---③
   重排序为①---③---②
 */
