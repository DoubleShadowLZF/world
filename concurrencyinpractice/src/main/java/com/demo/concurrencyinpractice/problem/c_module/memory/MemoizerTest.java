package com.demo.concurrencyinpractice.problem.c_module.memory;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigInteger;

@Slf4j
public class MemoizerTest {

    /**
     * 效率测试
     * 场景性能：
     * 如果compute 方法涉及消耗性能的逻辑时，性能理论上 Memoizer > Memoizer3 > Memoizer2 > Memoizer1
     * 如果未涉及到大的消耗性能时，四者在性能上并无明显的区别
     */
    @Test
    public void test(){
        ExpensiveFunction expensiveFunction = new ExpensiveFunction();
        Memoizer1<String, BigInteger> memoizer1 = new Memoizer1<>(expensiveFunction);
        compulate(memoizer1);
        Memoizer2<String, BigInteger> memoizer2 = new Memoizer2<>(expensiveFunction);
        compulate(memoizer2);
        Memoizer3<String, BigInteger> memoizer3 = new Memoizer3<>(expensiveFunction);
        compulate(memoizer3);
        Memoizer<String, BigInteger> memoizer = new Memoizer<>(expensiveFunction);
        compulate(memoizer);
    }

    public void compulate(Computable<String,BigInteger> memoizer) {
//        Memoizer<String, BigInteger> memoizer = new Memoizer<>(expensiveFunction);
        String key = "12345";
        long noCacheTime = 0;
        long cacheTime = 0 ;
        try {
            for (int i = 0; i < 10000; i++) {

            long start = System.nanoTime();
            BigInteger compute = memoizer.compute(key);
            long end = System.nanoTime();
            BigInteger compute1 = memoizer.compute(key);
            long end1 = System.nanoTime();
            noCacheTime += end - start;
            cacheTime += end1 - end;
            }
            log.debug("未从缓存中取得数据时间：{}", noCacheTime/100);
            log.debug("从缓存中取得数据时间：{}", cacheTime/100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
