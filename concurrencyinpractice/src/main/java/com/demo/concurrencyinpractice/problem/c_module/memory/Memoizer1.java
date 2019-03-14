package com.demo.concurrencyinpractice.problem.c_module.memory;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.GuardedBy;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

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
 *     缺点：非线程安全
 * </p>
 * @param <A> 输入类型
 * @param <V> 输出类型
 */
@Slf4j
public class Memoizer1<A,V> implements Computable<A,V> {

    @GuardedBy("this")
    private final Map<A,V> cache = new HashMap<A,V>();
    private final Computable<A,V> c;

    public Memoizer1(Computable<A,V> c){
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result =cache.get(arg);
        if(result == null){
            result = c.compute(arg);
            cache.put(arg,result);
        }
        return result;
    }

    public static void main(String[] args) {
        ExpensiveFunction expensiveFunction = new ExpensiveFunction();
        Memoizer1 memorizer = new Memoizer1<String, BigInteger>(expensiveFunction);
        String key = "1000000000";
        try {
            long start = System.nanoTime();
            Object aDouble = memorizer.compute(key);
            long end = System.nanoTime();
            log.debug("未使用缓存得到结果{},计算时间：{}",aDouble,end-start);
            aDouble = memorizer.compute(key);
            long end1 = System.nanoTime();
            log.debug("使用缓存得到结果{}，计算时间：{}",aDouble,end1-end);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
