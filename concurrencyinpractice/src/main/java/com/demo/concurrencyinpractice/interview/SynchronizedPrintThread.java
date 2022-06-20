package com.demo.concurrencyinpractice.interview;

/**
 * ClassName: SynchronizedPrintThread <br>
 * Description: 使用 synchronized + await/notifyAll 来实现，用三个线程按顺序循环打印 abc 三个字母，比如 abcabcabc<br>
 * date: 2022/6/20 21:18<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class SynchronizedPrintThread extends Thread {
  private static volatile Integer count = 0;

  private String name;
  private Integer state;
  private Object ao;
  private Object bo;

  public SynchronizedPrintThread(String name, Integer state, Object ao, Object bo) {
    this.name = name;
    this.state = state;
    this.ao = ao;
    this.bo = bo;
  }

  @Override
  public void run() {
    while (count < 7) {
      try {
        synchronized (ao) {
          while (count % 3 != this.state) {
            ao.wait();
          }
        }
        count++;
        System.out.println(name);
        synchronized (bo) {
          bo.notify();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Object ao = new Object();
    Object bo = new Object();
    Object co = new Object();
    SynchronizedPrintThread c = new SynchronizedPrintThread("C", 2, co, ao);
    SynchronizedPrintThread b = new SynchronizedPrintThread("B", 1, bo, co);
    SynchronizedPrintThread a = new SynchronizedPrintThread("A", 0, ao, bo);

    c.start();
    Thread.sleep(20);
    b.start();
    Thread.sleep(20);
    a.start();
    Thread.sleep(20);
  }
}
