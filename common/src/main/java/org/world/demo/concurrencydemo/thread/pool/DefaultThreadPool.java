package org.world.demo.concurrencydemo.thread.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description 线程池实现
 * 客户端通过execute(Job)方法将Job提交入线程池执行，而客户端自身不用等待Job的执行完成。
 * 除了execute(Job)方法以外，线程池接口提供了增大/减少工作者线程以及关闭线程池的方法。
 * 这里工作者线程代表着一个重复执行Job的线程，而每个由客户端提交的Job都将进入到一个工作队列中
 * 等待工作者线程的处理。
 * 添加一个job后，对工作队列jobs调用了其notify()方法，而不是notifyAll方法，
 * 因为能够确定有工作者线程被唤醒，这时使用notify()方法将会比notifyAll()方法获得更小的开销
 * （避免将等待队列中的线程全部移动到阻塞队列中）。
 * 描述：
 * 线程池的本质就是使用一个线程安全的工作队列连接工作者线程和客户端线程，
 * 客户端线程将任务放入工作队列后便返回，而工作者线程则不断地从工作队列上取出工作并执行。
 * 当工作队列为空时，所有的工作者线程均等待在工作队列上，当有客户端提交了一个任务之后，
 * 会通知任意一个工作者线程，随着大量的任务被提交，更多的工作者线程会被唤醒。
 * @Author Double
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
	/**
	 * 线程池最大限制数
	 */
	private static final int MAX_WORKER_NUMBERS = 10;

	/**
	 * 线程池默认的数量
	 */
	private static final int DEFAULT_WORKER_NUMBERS = 5;

	/**
	 * 线程池最小的数量
	 */
	private static final int MIN_WORKED_NUMBERS = 1;

	/**
	 * 这是一个工作列表，将会向里面插入工作
	 */
	private final LinkedList<Job> jobs = new LinkedList<>();

	/**
	 * 工作者列表
	 */
	private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

	/**
	 * 工作者线程的数量
	 */
	private int workerNum = DEFAULT_WORKER_NUMBERS;

	/**
	 * 线程编号生成
	 */
	private AtomicLong threadNum = new AtomicLong();

	public DefaultThreadPool() {
		initializeWorkers(DEFAULT_WORKER_NUMBERS);
	}

	public DefaultThreadPool(int num) {
		workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKED_NUMBERS ? MIN_WORKED_NUMBERS : num;
		initializeWorkers(workerNum);
	}

	/**
	 * 从线程池的实现可以看到，当客户端调用execute(Job)方法是，会不断地向任务列表jobs中添加Job，
	 * 而每个工作者线程会不断地从jobs上取出一个Job进行执行，当jobs为空时，工作者线程进入等待状态。
	 *
	 * @param job
	 */
	@Override
	public void execute(Job job) {
		if (job != null) {
			//添加一个工作，然后进行通知
			synchronized (jobs) {
				jobs.addLast(job);
				jobs.notify();
			}
		}
	}

	@Override
	public void shutdown() {
		for (Worker worker : workers) {
			worker.shutdown();
		}
	}

	@Override
	public void addWorkers(int num) {
		synchronized (jobs) {
			//限制新增的Worker数量不能超过最大值
			if (num + this.workerNum > MAX_WORKER_NUMBERS) {
				num = MAX_WORKER_NUMBERS - this.workerNum;
			}
			initializeWorkers(num);
			this.workerNum += num;
		}
	}

	@Override
	public int getJobSize() {
		return jobs.size();
	}

	private void initializeWorkers(int num) {
		for (int i = 0; i < num; i++) {
			Worker worker = new Worker();
			workers.add(worker);
			Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
			thread.start();
		}
	}

	public void removeWorker(int num) {
		synchronized (jobs) {
			if (num > this.workerNum) {
				throw new IllegalArgumentException("beyond workNum");
			}
			//按照给定的数量停止Worker
			int count = 0;
			while (count < num) {
				Worker worker = workers.get(count);
				if (workers.remove(worker)) {
					worker.shutdown();
					count++;
				}
			}
			this.workerNum -= count;
		}
	}

	/**
	 * 工作者，负责消费任务
	 */
	class Worker implements Runnable {
		//是否工作
		private volatile Boolean running = true;

		@Override
		public void run() {
			while (running) {
				Job job = null;
				synchronized (jobs) {
					//如果工作者列表是空的，那么就wait
					while (jobs.isEmpty()) {
						try {
							jobs.wait();
						} catch (InterruptedException ex) {
							//感知到外部对WorkerThread的中断操作，返回
							Thread.currentThread().interrupt();
							return;
						}
					}
					//取出一个Job
					job = jobs.removeFirst();
				}
				if (job != null) {
					try {
						job.run();
					} catch (Exception e) {
						//忽略Job执行中的Exception
						e.printStackTrace();
					}
				}
			}
		}

		public void shutdown() {
			running = false;
		}
	}


}
