package com.demo.concurrencyinpractice.problem.e_cancle;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * 不可靠的取消操作将把生产者置于阻塞的操作中（不要这么做）
 */
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    private volatile boolean cancelled =false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }

    public void run(){
        try{
            BigInteger p = BigInteger.ONE;
            while( !cancelled){
                queue.put(p = p.nextProbablePrime());
            }
        }catch (InterruptedException consumed){
            cancelled = true; //importance
        }
    }

    public void cancel(){
        cancelled = true;
    }
}
