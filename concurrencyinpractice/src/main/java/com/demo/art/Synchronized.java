package com.demo.art;

/**
 * @author Double
 * @Function TODO
 */
public class Synchronized {
    public static void main(String[] args) {
        synchronized (Synchronized.class){
            m();
        }
    }

    public static synchronized void m(){}
}
