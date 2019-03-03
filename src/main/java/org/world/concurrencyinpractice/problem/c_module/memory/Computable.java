package org.world.concurrencyinpractice.problem.c_module.memory;

import org.apache.tomcat.jni.Error;

import java.util.concurrent.ExecutionException;

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
