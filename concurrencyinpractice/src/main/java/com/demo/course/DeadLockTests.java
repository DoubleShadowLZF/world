package com.demo.course;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: DeadLockTests <br>
 * Description: <br>
 * date: 2020/9/7 23:10<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Slf4j
public class DeadLockTests {

  public static void main(String[] args) throws InterruptedException {
    ReentrantLock lock1 = new ReentrantLock();
    ReentrantLock lock2 = new ReentrantLock();

    Task task1 = new Task(lock1, lock2);
    Task task2 = new Task(lock2, lock1);

    task1.start();
    task2.start();

    TimeUnit.SECONDS.sleep(10);
  }

  private static class Task extends Thread {
    private ReentrantLock lock1 = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    public Task(ReentrantLock lock1, ReentrantLock lock2) {
      this.lock1 = lock1;
      this.lock2 = lock2;
    }

    public void deadThread() throws InterruptedException {
      while (true) {
        lock2.lock();
        log.info(" lock {} is locked ...", lock2.hashCode());
        //
        TimeUnit.SECONDS.sleep(1); // 此处应是业务逻辑
        lock1.lock();
        log.info("lock {} is locked ...", lock1.hashCode());
      }
    }

    @Override
    public void run() {
      try {
        deadThread();
      } catch (InterruptedException e) {
        log.error(ExceptionUtils.getFullStackTrace(e));
      }
    }
  }
}
