package com.demo.concurrencyinpractice.problem.e_cancle;

import javax.annotation.concurrent.GuardedBy;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 使用 volatile 类型的域来保存取消状态
 */
public class PrimeGenerator implements Runnable {

    @GuardedBy("this")
    private final List<BigInteger> primes = new ArrayList();

    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while(!cancelled){
            p = p.nextProbablePrime();
            synchronized(this){
                primes.add(p);
            }
        }
    }

    public void cancel(){
        cancelled = true;
    }

    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primes);
    }

    /**
     * 一个仅运行一秒钟的素数生成器
     * @return
     * @throws InterruptedException
     */
    public List<BigInteger> aSencondOfPrimes() throws InterruptedException{
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try{
            SECONDS.sleep(1);
        }finally {
            generator.cancel();
        }
        return generator.get();
    }
}
