package com.demo.concurrencyinpractice.problem.c_module.memory;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.GuardedBy;
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
 *     优化 Memoizer3 ，ConcurrentMap 中的原子方法 putIfAbsent，避免重复缓存相同的数据。
 * </p>
 * @param <A> 输入类型
 * @param <V> 输出类型
 * @see Memoizer2
 */
@Slf4j
public class Memoizer<A,V> implements Computable<A,V> {

    @GuardedBy("this")
    private final Map<A, FutureTask<V>> cache = new HashMap<A,FutureTask<V>>();
    private final Computable<A,V> c;

    public Memoizer(Computable<A,V> c){
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
            result = cache.putIfAbsent(arg, ft);
            //在这里调用c.compute
            if( result == null){
                result = ft;
                ft.run();
            }
        }
        try {
            return result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
