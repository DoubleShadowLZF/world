package org.world.demo.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @Description
 */
public class ThreadPoolUtil {

	/**
	 * 使用 corePoolSize线程数并发地执行treadSize个任务task
	 * @param task 执行的任务
	 * @param threadSize 执行的任务数
	 * @param corePoolSize 同一时间并行的数量,不能超过200
*
	 */
	public static void RunThread(Runnable task,long threadSize,int corePoolSize){
		if(corePoolSize > 200){
			throw new RuntimeException("corePoolSize最大只能设置到200");
		}
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, 200, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		for (int i = 0; i < threadSize; i++) {
			pool.execute(task);
		}
		pool.shutdown();
	}
}
