package com.demo.art;

import com.demo.art.utils.Sleep;
import lombok.extern.slf4j.Slf4j;

/**
 * @Function ThreadLocal 的使用
 * @author Double
 */
@Slf4j
public class Profilter {
    private Profilter(){}

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) {
        Profilter.begin();
        Sleep.s(1);
        log.info("时间间隔：{}",Profilter.end());
    }

}
