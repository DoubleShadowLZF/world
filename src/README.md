#@GuardedBy
@GuardedBy(lock)，这意味着有保护的字段或方法只能在线程持有锁时被某些线程访问。 我们可以将锁定指定为以下类型：

this : 在其类中定义字段的对象的固有锁。
class-name.this : 对于内部类，可能有必要消除“this”的歧义; class-name.this指定允许您指定“this”引用的意图。
                                itself ： 仅供参考字段; 字段引用的对象。
                                field-name ： 锁对象由字段名指定的（实例或静态）字段引用。
class-name.field-name ： 锁对象由class-name.field-name指定的静态字段引用。
method-name() ： 锁对象通过调用命名的nil-ary方法返回。
class-name ：指定类的Class对象用作锁定对象。

#Thread.yield()
Java线程中的Thread.yield( )方法，译为线程让步。顾名思义，就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，
让自己或者其它的线程运行，注意是让自己或者其他线程运行，并不是单纯的让给其他线程。
yield()的作用是让步。它能让当前线程由“运行状态”进入到“就绪状态”，从而让其它具有相同优先级的等待线程获取执行权；
但是，并不能保证在当前线程调用yield()之后，其它具有相同优先级的线程就一定能获得执行权；也有可能是当前线程又进入到“运行状态”继续运行！

#@Immutable 代表不可变类

