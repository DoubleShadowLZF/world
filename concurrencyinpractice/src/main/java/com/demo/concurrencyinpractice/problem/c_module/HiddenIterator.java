package com.demo.concurrencyinpractice.problem.c_module;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 隐藏在字符串连接中的迭代操作
 */
@NotThreadSafe
public class HiddenIterator {
    @GuardedBy("this")
    private final Set<Integer> set = new HashSet<Integer>();

    public synchronized void add(Integer i){
        set.add(i);
    }

    public synchronized void remove(Integer i){
        set.remove(i);
    }

    /**
     * set转字符串，使用到了AbstractCollection类中的toString()方法，
     *  其中，使用到了迭代操作，可能会造成ConcurrentModificationException，
     * @See HashSet
     *
     */
    public void addTenThings(){
        Random r= new Random();
        for (int i = 0; i < 10; i++) {
            add(r.nextInt());
        }
        System.out.println("DEBUG: added ten element to "+set);
    }


    public static void main(String[] args) {
        HiddenIterator hiddenIterator = new HiddenIterator();
        hiddenIterator.addTenThings();
    }
}
