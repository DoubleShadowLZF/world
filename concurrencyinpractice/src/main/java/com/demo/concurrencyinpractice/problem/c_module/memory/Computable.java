package com.demo.concurrencyinpractice.problem.c_module.memory;

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
