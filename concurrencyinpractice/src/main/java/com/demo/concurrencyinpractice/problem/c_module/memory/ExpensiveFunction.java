package com.demo.concurrencyinpractice.problem.c_module.memory;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        Thread.sleep(100);
        return new BigInteger(arg);
    }
}
