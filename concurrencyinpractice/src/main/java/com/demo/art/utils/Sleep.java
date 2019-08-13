package com.demo.art.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author Double
 * @Function TODO
 */
public class Sleep {
    /**
     * 睡眠 seconds 秒
     * @param seconds
     */
    public static void s(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 睡眠 ms 秒
     *
     * @param ms
     */
    public static void ms(int ms){
        try {
            TimeUnit.MICROSECONDS.sleep(ms);
        } catch (InterruptedException e) {
        }

    }
}
