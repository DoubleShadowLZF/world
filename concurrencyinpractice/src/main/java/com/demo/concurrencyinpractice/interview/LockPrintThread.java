package com.demo.concurrencyinpractice.interview;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: PrintThread <br>
 * Description: 使用 Lock + Condition 来实现，用三个线程按顺序循环打印 abc 三个字母，比如 abcabcabc<br>
 * date: 2022/6/20 20:50<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class LockPrintThread extends Thread {

  private ReentrantLock lock;
  private Condition conditionA;
  private Condition conditionB;
  private String name;
  private Integer state;
  private AtomicInteger count;

  public LockPrintThread(
      ReentrantLock lock,
      Condition conditionA,
      Condition conditionB,
      String name,
      Integer state,
      AtomicInteger count) {
    this.lock = lock;
    this.conditionA = conditionA;
    this.conditionB = conditionB;
    this.name = name;
    this.state = state;
    this.count = count;
  }

  @Override
  public void run() {
    lock.lock();
    try {
      while (count.get() < 7) {
        while (count.get() % 3 != this.state) {
          conditionA.await();
        }
        System.out.println(name + count.get());
        count.incrementAndGet();
        conditionB.signal();
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ReentrantLock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();
    AtomicInteger count = new AtomicInteger(0);
    new LockPrintThread(lock, conditionC, conditionA, "C", 2, count).start();
    Thread.sleep(20);
    new LockPrintThread(lock, conditionB, conditionC, "B", 1, count).start();
    Thread.sleep(20);
    new LockPrintThread(lock, conditionA, conditionB, "A", 0, count).start();
  }
}
