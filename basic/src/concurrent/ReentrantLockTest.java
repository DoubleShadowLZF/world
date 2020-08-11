package concurrent;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ClassName: ReentrantLockTest <br>
 * Description: <br>
 * date: 2020/8/11 20:33<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class ReentrantLockTest {

  @Test
  public void multipleWriteUnlock() {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    writeLock.lock();

    int holdCount = writeLock.getHoldCount();
    System.out.println("holdCount : " + holdCount);

    boolean heldByCurrentThread = writeLock.isHeldByCurrentThread();
    System.out.println("heldByCurrentThread : " + heldByCurrentThread);

    writeLock.unlock();
    if (writeLock.isHeldByCurrentThread()) {
      // 如果没有检测写锁的是否被该线程所持有，则直接抛出 IllegalMonitorStateException
      // 写锁只能 unlock 对应lock 次数
      writeLock.unlock();
    }

    heldByCurrentThread = writeLock.isHeldByCurrentThread();
    System.out.println("heldByCurrentThread : " + heldByCurrentThread);
  }

  @Test
  public void multipleReadUnlock() {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    readLock.lock();

    readLock.unlock();

    lock.writeLock().lock();
    lock.writeLock().unlock();
    // 读锁 lock 几次对应只能 unlock 几次，如果 unlock 次数大于 lock 次数，
    // 则抛出异常 IllegalMonitorStateException: attempt to unlock read lock, not locked by current thread
    readLock.unlock();
  }

  @Test
  public void whenTryLockFail() throws InterruptedException {
    Consumer consumer = new Consumer();
    Producer producer = new Producer();

    int index = 0;
    while (true) {
      int finalIndex = index;
      CompletableFuture.runAsync(
          () -> {
            producer.product("job " + finalIndex);
          });
      index++;
      CompletableFuture.runAsync(
          () -> {
            try {
              consumer.consumer();
              TimeUnit.MICROSECONDS.sleep(500);
            } catch (Exception e) {
              e.printStackTrace();
            }
          });
      TimeUnit.SECONDS.sleep(1);
    }
  }

  private static class Worker implements Runnable {
    protected static ReentrantReadWriteLock resource = new ReentrantReadWriteLock();
    protected static BlockingQueue<String> queue = new LinkedBlockingQueue();
    protected static ReentrantReadWriteLock.ReadLock r = resource.readLock();
    protected static ReentrantReadWriteLock.WriteLock w = resource.writeLock();

    protected Integer jobCount = 0;

    @Override
    public void run() {}
  }

  private static class Consumer extends Worker {
    public void consumer() throws Exception {
      //      r.lock(); //读锁阻塞
      boolean hasGotLock = r.tryLock(2, TimeUnit.SECONDS);

      try {
        if (hasGotLock)
          if (!queue.isEmpty()) {
            Object job = queue.take();
            System.out.println(">>> " + Thread.currentThread().getName() + " take the " + job);
          }
      } finally {
        r.unlock();
      }
    }
  }

  private static class Producer extends Worker {
    public void product(String job) {
      w.lock();
      try {
        queue.add(job);
        // 模拟工作任务积累到一定程度后，照成的阻塞
        if (jobCount++ > 5) {
          TimeUnit.SECONDS.sleep(10);
        }
        System.out.println("<<< " + Thread.currentThread().getName() + " add the " + job);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        w.unlock();
      }
    }
  }
}
