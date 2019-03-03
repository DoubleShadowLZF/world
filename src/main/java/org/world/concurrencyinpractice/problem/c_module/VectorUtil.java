package org.world.concurrencyinpractice.problem.c_module;


/**
 * Vector上可能导致混乱结果的复合操作
 * 优化：在使用客户端加锁的Vector上的复合操作（   synchronized(list)  ）
 */
public class VectorUtil extends java.util.Vector {

    public static Object getLast(java.util.Vector list) {
        synchronized (list){
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }

    public static void deleteLast(java.util.Vector list){
        synchronized (list){
            int lastIndex = list.size()-1;
            list.remove(lastIndex);
        }
    }

    /**
     * 可能抛出ArrayIndexOutOfBoundsException 操作
     * 优化：带客户端加锁的迭代 （synchronized(vector)）
     * @param vector
     */
    public static void doIteration(java.util.Vector vector){
        synchronized (vector){
            for (int i = 0; i < vector.size(); i++) {
                doSomething();
            }
        }
    }

    public static void doSomething(){

    }
}
