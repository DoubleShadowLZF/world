package org.world.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.world.demo.concurrency.Mutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;

/**
 * @Function AQS 框架测试
 *
 * @author Double
 */
@Slf4j
public class MutexTest {

  private Lock lock;

  public MutexTest(Lock lock) {
    this.lock = lock;
  }

  public void runTask() {
    lock.lock();
    try {
        log.info("{} 执行任务中...",Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(3);
        log.info("{} 任务完成...",Thread.currentThread().getName());
    } catch (Exception e) {
        log.error("{}",e);
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
      Lock lock = new Mutex();
      final MutexTest test = new MutexTest(lock);
    for (int i = 0; i < 5; i++) {
      new Thread(new Runnable() {
          @Override
          public void run() {
              test.runTask();
          }
      },"线程" + i).start();
    }
  }
}
