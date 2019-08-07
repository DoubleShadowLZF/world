package org.world.demo.concurrency;

/**
 * 线程间通信
 *
 * @Description 同步块实现使用了 monitorenter 和 monitorexit 指令，
 * 而同步方法则是依靠方法修饰符上的 ACC_SYNCHRONIZED 来完成的。
 * 无论采用哪种方式，其本质是对一个对象的监视器（monitor）进行获取，而这个获取过程是排他的，
 * 也就是同一时刻只能有一个线程获取到由 synchronized 所保护对象的监视器。
 * 任意一个对象都拥有自己的监视器，当这个对象由同步块或者这个对象的同步方法调用时，
 * 执行方法的线程必须先获取到该对象的监视器才能进入同步块或者同步方法，
 * 而没有获取到监视器（执行该方法）的线程将会阻塞在同步块和不同方法的入口处，
 * 进入BLOCKED 状态。
 * @Author Double
 */
public class Synchronized {
	public static void main(String[] args) {
		//对 Synchronized Class 对象进行加锁
		synchronized (Synchronized.class) {

		}
		//静态同步方法，对Synchronized Class 对象进行加锁
		m();
	}

	public static synchronized void m() {
	}
}

//	D:\Document\demo\world\target\classes\org\world\demo\concurrency>javap -v Synchronized.class
//	Classfile /D:/Document/demo/world/target/classes/org/world/demo/concurrency/Synchronized.class
//	Last modified 2019-1-6; size 606 bytes
//			MD5 checksum d4c8f5a05d3bcf2bcaa01de8f815af46
//			Compiled from "Synchronized.java"
//	public class org.world.demo.concurrency.Synchronized
//			minor version: 0
//			major version: 52
//			flags: ACC_PUBLIC, ACC_SUPER
//			Constant pool:
//			#1 = Methodref          #4.#23         // java/lang/Object."<init>":()V
//			#2 = Class              #24            // org/world/demo/concurrency/Synchronized
//			#3 = Methodref          #2.#25         // org/world/demo/concurrency/Synchronized.m:()V
//			#4 = Class              #26            // java/lang/Object
//			#5 = Utf8               <init>
//	   #6 = Utf8               ()V
//			   #7 = Utf8               Code
//			   #8 = Utf8               LineNumberTable
//			   #9 = Utf8               LocalVariableTable
//			   #10 = Utf8               this
//			   #11 = Utf8               Lorg/world/demo/concurrency/Synchronized;
//			   #12 = Utf8               main
//			   #13 = Utf8               ([Ljava/lang/String;)V
//			   #14 = Utf8               args
//			   #15 = Utf8               [Ljava/lang/String;
//			   #16 = Utf8               StackMapTable
//			   #17 = Class              #15            // "[Ljava/lang/String;"
//			   #18 = Class              #26            // java/lang/Object
//			   #19 = Class              #27            // java/lang/Throwable
//			   #20 = Utf8               m
//			   #21 = Utf8               SourceFile
//			   #22 = Utf8               Synchronized.java
//			   #23 = NameAndType        #5:#6          // "<init>":()V
//			   #24 = Utf8               org/world/demo/concurrency/Synchronized
//			   #25 = NameAndType        #20:#6         // m:()V
//			   #26 = Utf8               java/lang/Object
//			   #27 = Utf8               java/lang/Throwable
//			   {
//	public org.world.demo.concurrency.Synchronized();
//			descriptor: ()V
//			flags: ACC_PUBLIC
//			Code:
//			stack=1, locals=1, args_size=1
//			0: aload_0
//			1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//			4: return
//			LineNumberTable:
//			line 7: 0
//			LocalVariableTable:
//			Start  Length  Slot  Name   Signature
//			0       5     0  this   Lorg/world/demo/concurrency/Synchronized;
//
//	public static void main(java.lang.String[]);
//			descriptor: ([Ljava/lang/String;)V
//			flags: ACC_PUBLIC, ACC_STATIC
//			Code:
//			stack=2, locals=3, args_size=1
//			0: ldc           #2                  // class org/world/demo/concurrency/Synchronized
//			2: dup
//			3: astore_1
//			4: monitorenter     //监视器进入，获取锁
//			5: aload_1
//			6: monitorexit      //监视器退出，释放锁9
//			7: goto          15
//			10: astore_2
//			11: aload_1
//			12: monitorexit
//			13: aload_2
//			14: athrow
//			15: invokestatic  #3                  // Method m:()V
//			18: return
//			Exception table:
//			from    to  target type
//			5     7    10   any
//			10    13    10   any
//			LineNumberTable:
//			line 10: 0
//			line 12: 5
//			line 14: 15
//			line 15: 18
//			LocalVariableTable:
//			Start  Length  Slot  Name   Signature
//			0      19     0  args   [Ljava/lang/String;
//			StackMapTable: number_of_entries = 2
//			frame_type = 255 /* full_frame */
//			offset_delta = 10
//			locals = [ class "[Ljava/lang/String;", class java/lang/Object ]
//			stack = [ class java/lang/Throwable ]
//			frame_type = 250 /* chop */
//			offset_delta = 4
//
//	public static synchronized void m();
//			descriptor: ()V
//			flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
//			Code:
//			stack=0, locals=0, args_size=0
//			0: return
//			LineNumberTable:
//			line 16: 0
//			}
//			SourceFile: "Synchronized.java"

