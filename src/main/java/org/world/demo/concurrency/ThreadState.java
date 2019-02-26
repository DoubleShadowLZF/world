package org.world.demo.concurrency;

import org.world.demo.utils.SleepUtils;

/**
 * 线程状态
 * @Description
 */
public class ThreadState {
	public static void main(String[] args) {
		new Thread(new TimeWaiting(),"TimeWaitingThread").start();
		new Thread(new Waiting(),"WaitingThread").start();
		//使用两个Blocked线程，一个获取锁成功，另一个被阻塞
		new Thread(new Blocked(),"BlockedThread-1").start();
		new Thread(new Blocked(),"BlockedThread-2").start();
	}

	/**
	 * 该线程将不断地进行睡眠
	 */
	private static class TimeWaiting implements Runnable {
		@Override
		public void run() {
			while (true){
				SleepUtils.second(100);
			}
		}
	}

	/**
	 * 该线程在Waiting.class实例上等待
	 */
	static class Waiting implements Runnable{

		@Override
		public void run() {
			while(true){
				synchronized (Waiting.class){
					try{
						Waiting.class.wait();
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 该线程在Blocked.class实例上加锁后，不会释放该锁
	 */
	static class Blocked implements Runnable{
		@Override
		public void run(){
			synchronized (Blocked.class){
				SleepUtils.second(1000);
			}
		}
	}

}


/*
D:\Document\0.development\Java\jdk1.8.0_131\bin>jps
10336
25776 Launcher
25444 Jps
17480 jar
22856 ThreadState
2492 jar

D:\Document\0.development\Java\jdk1.8.0_131\bin>jstack 22856
2019-01-06 17:48:55
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.131-b11 mixed mode):

"DestroyJavaVM" #16 prio=5 os_prio=0 tid=0x000000001b480800 nid=0x2ef0 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

//BlockedThread-2 线程阻塞在获取Blocked.class示例的锁上
"BlockedThread-2" #15 prio=5 os_prio=0 tid=0x000000001b483800 nid=0x4f4 waiting for monitor entry [0x000000001bf5f000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at org.world.demo.concurrency.ThreadState$Blocked.run(ThreadState.java:56)
        - waiting to lock <0x00000000d63ad5a0> (a java.lang.Class for org.world.demo.concurrency.ThreadState$Blocked)
        at java.lang.Thread.run(Thread.java:748)

//BlockedThread-1 线程获取到了 Blocked.class 的锁
"BlockedThread-1" #14 prio=5 os_prio=0 tid=0x000000001b484000 nid=0xa74 waiting on condition [0x000000001be5e000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(T·123(⊙o⊙)…imeUnit.java:386)
        at org.world.demo.utils.SleepUtils.second(SleepUtils.java:11)
        at org.world.demo.concurrency.ThreadState$Blocked.run(ThreadState.java:56)
        - locked <0x00000000d63ad5a0> (a java.lang.Class for org.world.demo.concurrency.ThreadState$Blocked)
        at java.lang.Thread.run(Thread.java:748)

//WaitingThread 线程在waiting实例上等待
"WaitingThread" #13 prio=5 os_prio=0 tid=0x000000001b48b000 nid=0x4524 in Object.wait() [0x000000001bd5e000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d63aa968> (a java.lang.Class for org.world.demo.concurrency.ThreadState$Waiting)
        at java.lang.Object.wait(Object.java:502)
        at org.world.demo.concurrency.ThreadState$Waiting.run(ThreadState.java:40)
        - locked <0x00000000d63aa968> (a java.lang.Class for org.world.demo.concurrency.ThreadState$Waiting)
        at java.lang.Thread.run(Thread.java:748)

//TimeWaitingThread线程处于超时等待
"TimeWaitingThread" #12 prio=5 os_prio=0 tid=0x0000000019a5e800 nid=0x3f80 waiting on condition [0x000000001b45f000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at org.world.demo.utils.SleepUtils.second(SleepUtils.java:11)
        at org.world.demo.concurrency.ThreadState$TimeWaiting.run(ThreadState.java:25)
        at java.lang.Thread.run(Thread.java:748)

"Service Thread" #11 daemon prio=9 os_prio=0 tid=0x00000000198f9000 nid=0x5f98 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread3" #10 daemon prio=9 os_prio=2 tid=0x00000000198d8000 nid=0x5f14 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x00000000198d7000 nid=0x6660 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x00000000198d5800 nid=0x5ec0 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x00000000198d2800 nid=0x62b8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x000000001986c800 nid=0x51c0 runnable [0x000000001ad5e000]
   java.lang.Thread.State: RUNNABLE
        at java.net.SocketInputStream.socketRead0(Native Method)
        at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
        at java.net.SocketInputStream.read(SocketInputStream.java:171)
        at java.net.SocketInputStream.read(SocketInputStream.java:141)
        at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
        at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
        at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
        - locked <0x00000000d632b548> (a java.io.InputStreamReader)
        at java.io.InputStreamReader.read(InputStreamReader.java:184)
        at java.io.BufferedReader.fill(BufferedReader.java:161)
        at java.io.BufferedReader.readLine(BufferedReader.java:324)
        - locked <0x00000000d632b548> (a java.io.InputStreamReader)
        at java.io.BufferedReader.readLine(BufferedReader.java:389)
        at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:64)

"Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x0000000019767800 nid=0x6110 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000019713000 nid=0xef4 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=1 tid=0x00000000196f3800 nid=0x4034 in Object.wait() [0x000000001aa5f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d6108ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
        - locked <0x00000000d6108ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

"Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000002efe000 nid=0x48e4 in Object.wait() [0x000000001a95f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d6106b68> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x00000000d6106b68> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

"VM Thread" os_prio=2 tid=0x0000000018016800 nid=0x5f50 runnable

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000002e1a800 nid=0x5270 runnable

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x0000000002e1c000 nid=0x3ab4 runnable

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000002e1d800 nid=0x2eb8 runnable

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000002e1f000 nid=0x62c0 runnable

"GC task thread#4 (ParallelGC)" os_prio=0 tid=0x0000000002e21800 nid=0x12b0 runnable

"GC task thread#5 (ParallelGC)" os_prio=0 tid=0x0000000002e23800 nid=0x56b4 runnable

"GC task thread#6 (ParallelGC)" os_prio=0 tid=0x0000000002e27000 nid=0x5d10 runnable

"GC task thread#7 (ParallelGC)" os_prio=0 tid=0x0000000002e28000 nid=0x64bc runnable

"GC task thread#8 (ParallelGC)" os_prio=0 tid=0x0000000002e29000 nid=0x6188 runnable

"GC task thread#9 (ParallelGC)" os_prio=0 tid=0x0000000002e2a800 nid=0x5c7c runnable

"VM Periodic Task Thread" os_prio=2 tid=0x00000000199f1000 nid=0x53e4 waiting on condition

JNI global references: 33
 */
