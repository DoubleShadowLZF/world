package org.world.demo.concurrency;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 独占锁
 *
 * @author Double4
 */
public class Mutex implements Lock, Serializable {
  /** 同步器 */
  private static class Sync extends AbstractQueuedSynchronizer {

    /**
     * @return 是否处于占用状态
     */
    @Override
    @NotNull
    protected boolean isHeldExclusively() {
      return getState() == 1;
    }

    /**
     * 当状态为 0 的时候获取锁
     *
     * @param acquires 期望值，独占锁，只支持 1
     * @return 可以占用锁返回 true,否则返回 false
     */
    @Override
    protected boolean tryAcquire(int acquires) {
      assert acquires == 1;
      if (compareAndSetState(0, 1)) { //如果当期的状态值为0，则将同步状态原子地设置为1
        // 将当前线程设置为独占线程
        setExclusiveOwnerThread(Thread.currentThread());
        return true;
      }
      return false;
    }

    /**
     * 释放锁，将状态设置为 0
     *
     * @param releases 释放的状态值
     * @return 释放成功为 true，失败为 false
     */
    @Override
    protected boolean tryRelease(int releases) {
      assert releases == 1;
      if (getState() == 0) {
        throw new IllegalMonitorStateException();
      }
      // 将线程或状态，重置为初始值
      setExclusiveOwnerThread(null);
      setState(0);
      return true;
    }

    /**
     * @return 返回一个 Condition ，每个condition 都包含了一个 condition 队列
     */
    Condition newCondition() {
      return new ConditionObject();
    }
  }

  /** 仅需要将操作代理到 Sync 上即可 */
  private final Sync sync = new Sync();

  @Override
  public void lock() {
    sync.acquire(1);
  }

  @Override
  public boolean tryLock() {
    return sync.tryAcquire(1);
  }

  @Override
  public void unlock() {
    sync.release(1);
  }

  @Override
  public Condition newCondition() {
    return sync.newCondition();
  }

  public boolean isLocked() {
    return sync.isHeldExclusively();
  }

  public boolean hasQueueThreads() {
    return sync.hasQueuedPredecessors();
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    sync.acquireInterruptibly(1);
  }

  @Override

  public boolean tryLock(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
    return sync.tryAcquireNanos(1, unit.toNanos(timeout));
  }
}
