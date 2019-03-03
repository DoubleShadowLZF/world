package org.world.concurrencyinpractice.problem.c_module.memory;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Error;
import org.world.util.ExceptionUtil;

import javax.annotation.concurrent.GuardedBy;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 记忆应用场景：
 * <p>
 *      使用HashMap和同步机制来初始化缓存
 * </p>
 *
 * <p>
 *      在ExpensiveFunction中实现的Computable，需要很长的时间来计算机结果，
 *      我们将创建一个Computable包装器，帮助记住之前的计算结果，
 *      并将缓存过程封装起来。
 * </p>
 * <p>
 *     优化 Memoizer2 ，使用 FutureTask 避免 compute 方法进行重复计算。
 *     FutureTask 表示一个计算过程，这个过程可能已经计算完成，也坑内正在进行。
 *     如果有结果可用，那么FutureTask.geet将立即返回结果，否则它会一直阻塞，直到结果计算出来再将其返回。
 *     缺点：
 *          存在两个线程计算出相同值的漏洞。这个漏洞的发生概率要远小于Memoizer2中发生的概率，
 *          但由于compute方法中的if代码块仍然是非原子（nonatomic）的“先检查再执行”操作，
 *          因此两个线程仍有可能在同一时间内调用compute来计算相同的值。即两者都没有在缓存中找到期望的值，
 *          因此都开始计算。
 *
 * </p>
 * @param <A> 输入类型
 * @param <V> 输出类型
 * @see Memoizer2
 */
@Slf4j
public class Memoizer3<A,V> implements Computable<A,V> {

    @GuardedBy("this")
    private final Map<A, FutureTask<V>> cache = new HashMap<A,FutureTask<V>>();
    private final Computable<A,V> c;

    public Memoizer3(Computable<A,V> c){
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException{
        Future<V> result =cache.get(arg);
        if(result == null){
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<>(eval);
            result = ft;
            cache.put(arg,ft);
            //在这里调用c.compute
            ft.run();
        }
        try {
            return result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
