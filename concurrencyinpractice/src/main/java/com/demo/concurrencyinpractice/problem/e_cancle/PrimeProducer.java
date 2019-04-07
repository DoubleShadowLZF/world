package com.demo.concurrencyinpractice.problem.e_cancle;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * 通过中断来取消
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue queue){
        this.queue = queue;
    }

    public void run(){
        try{
            BigInteger p = BigInteger.ONE;
            while(!Thread.currentThread().isInterrupted()){
                queue.put( p = p.nextProbablePrime());
            }
        }catch (InterruptedException consumed){
            //允许线程退出
        }
    }

    public void cancel(){
        interrupt();
    }
}
