package com.demo.art;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Double
 * @Function 线程状态
 */
public class ThreadState {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(new TimeWait(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }

    static class TimeWait implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    static class Waiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    static class Blocked implements Runnable {

        @Override
        public void run() {
            //此处应该锁住整个类（Blocked.class），而不能只是锁住某个对象（this）
            synchronized (Blocked.class) {
//                lock.lock();
//                try{
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
//                }finally {
//                    lock.unlock();
//                }
            }
        }
    }
}

/*
D:\Document\demo\world>jps
11584 ThreadState
17188
9400 Jpsj
1692 Launcher
19068 Launcher

D:\Document\demo\world>jstack 11584
2019-08-10 10:03:24
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.131-b11 mixed mode):

"DestroyJavaVM" #16 prio=5 os_prio=0 tid=0x0000000003099000 nid=0x4be8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"BlockedThread-2" #15 prio=5 os_prio=0 tid=0x00000000249cb000 nid=0x56c0 waiting on condition [0x00000000259ff000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.demo.art.ThreadState$Blocked.run(ThreadState.java:53)
        - locked <0x0000000740e46a60> (a com.demo.art.ThreadState$Blocked)
        at java.lang.Thread.run(Thread.java:748)

"BlockedThread-1" #14 prio=5 os_prio=0 tid=0x00000000248b8000 nid=0x36d4 waiting on condition [0x00000000258fe000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.demo.art.ThreadState$Blocked.run(ThreadState.java:53)
        - locked <0x0000000740e46810> (a com.demo.art.ThreadState$Blocked)
        at java.lang.Thread.run(Thread.java:748)

"WaitingThread" #13 prio=5 os_prio=0 tid=0x00000000248b7800 nid=0x3050 in Object.wait() [0x00000000257ff000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x0000000740e43e78> (a java.lang.Class for com.demo.art.ThreadState$Waiting)
        at java.lang.Object.wait(Object.java:502)
        at com.demo.art.ThreadState$Waiting.run(ThreadState.java:38)
        - locked <0x0000000740e43e78> (a java.lang.Class for com.demo.art.ThreadState$Waiting)
        at java.lang.Thread.run(Thread.java:748)

"TimeWaitingThread" #12 prio=5 os_prio=0 tid=0x00000000248b7000 nid=0x5228 waiting on condition [0x00000000256fe000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.demo.art.ThreadState$TimeWait.run(ThreadState.java:24)
        at java.lang.Thread.run(Thread.java:748)

"Service Thread" #11 daemon prio=9 os_prio=0 tid=0x000000002484a000 nid=0x55b8 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread3" #10 daemon prio=9 os_prio=2 tid=0x000000002477e800 nid=0x52a0 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x000000002477d800 nid=0x5290 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x000000002477d000 nid=0xcf8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x0000000024758800 nid=0x28d0 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x000000002344e000 nid=0x4f9c runnable [0x0000000024ffe000]
   java.lang.Thread.State: RUNNABLE
        at java.net.SocketInputStream.socketRead0(Native Method)
        at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
        at java.net.SocketInputStream.read(SocketInputStream.java:171)
        at java.net.SocketInputStream.read(SocketInputStream.java:141)
        at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
        at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
        at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
        - locked <0x0000000740f96d60> (a java.io.InputStreamReader)
        at java.io.InputStreamReader.read(InputStreamReader.java:184)
        at java.io.BufferedReader.fill(BufferedReader.java:161)
        at java.io.BufferedReader.readLine(BufferedReader.java:324)
        - locked <0x0000000740f96d60> (a java.io.InputStreamReader)
        at java.io.BufferedReader.readLine(BufferedReader.java:389)
        at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:64)

"Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000002319c800 nid=0x7c waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000023146000 nid=0x3f38 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=1 tid=0x0000000023123800 nid=0x3bf4 in Object.wait() [0x000000002449f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x0000000740b88ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
        - locked <0x0000000740b88ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

"Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000021a4e800 nid=0x36e0 in Object.wait() [0x000000002439e000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x0000000740b86b68> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x0000000740b86b68> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

"VM Thread" os_prio=2 tid=0x0000000023102800 nid=0x45e0 runnable

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x00000000030ad000 nid=0x5490 runnable

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x00000000030ae800 nid=0x5428 runnable

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x00000000030b0000 nid=0x43fc runnable

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x00000000030b1800 nid=0x2110 runnable

"GC task thread#4 (ParallelGC)" os_prio=0 tid=0x00000000030b4000 nid=0x40c4 runnable

"GC task thread#5 (ParallelGC)" os_prio=0 tid=0x00000000030b6000 nid=0x5450 runnable

"GC task thread#6 (ParallelGC)" os_prio=0 tid=0x00000000030b9800 nid=0x53bc runnable

"GC task thread#7 (ParallelGC)" os_prio=0 tid=0x00000000030ba800 nid=0x3904 runnable

"GC task thread#8 (ParallelGC)" os_prio=0 tid=0x00000000030bb800 nid=0x5520 runnable

"GC task thread#9 (ParallelGC)" os_prio=0 tid=0x00000000030bd000 nid=0x54e4 runnable

"VM Periodic Task Thread" os_prio=2 tid=0x00000000248cc000 nid=0x2c8c waiting on condition

JNI global references: 33

 */