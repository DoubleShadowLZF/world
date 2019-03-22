package org.springboot.distribute;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.distribute.lock.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DistributedLockTests {

    @Autowired
    private DistributedLock lock;

    private final String KEY_LOCK_INSERT = "KEY_LOCK_INSERT";
    private final String VALUE_LOCK_INSERT = "VALUE_LOCK_INSERT";
    /**
     * 分布式锁过期时间为3秒
     */
    private final Integer TIME_LOCK_INSERT = 3000;


    private Map<String,Object> cache = new LinkedHashMap<>();

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private Random random = new Random();

    /**
     * 以线程为服务，模拟分布式事务
     */
    @Test
    public void distributedLockTest() {
        for (int i = 0; i < 10; i++) {
            Runner runner = new Runner("Thread"+i);
            runner.start();
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{}",JSONObject.toJSON(cache));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Runner extends Thread{
        private String tName ;
        @Override
        public void run() {
            final String name = tName;
            log.info(name+"已经运行");
            synchronized (Runner.class){
                boolean isLock = lock.lock(KEY_LOCK_INSERT, VALUE_LOCK_INSERT, TIME_LOCK_INSERT);
                log.info(name+"尝试拿锁:"+System.currentTimeMillis());
                while(!isLock){
                    log.info(name+"等待拿锁:"+System.currentTimeMillis());
                    isLock = lock.lock(KEY_LOCK_INSERT, VALUE_LOCK_INSERT, TIME_LOCK_INSERT);
                }
            }
            log.info(name+"拿到锁:"+System.currentTimeMillis());
            try {
                Thread.sleep(random.nextInt(3)*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            cache.put(name,format.format(date));
            log.info(name+"释放锁:"+System.currentTimeMillis());
            lock.release(KEY_LOCK_INSERT,VALUE_LOCK_INSERT);
        }
    }
}
