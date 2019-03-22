package org.springboot.distribute.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 分布式锁，解决分布式事务
 * <p>
 * 利用redis setNx 指令，实现分布式锁
 * </p>
 */
@Component
@Slf4j
public class DistributedLock {

    @Autowired
    private RedisTemplate redisTemplate;

    private Long SUCCESS = 1L;
    private Long FAIL = 0L;

    private String KEY_DEFAULT = "KEY_DEFAULT";
    private String VALUE_DEFAULT = "VALUE_DEFAULT";
    private Integer TIME_DEFAULT = 3;


    /**
     * 取锁
     * @param key
     * @param value
     * @param expireTime 以秒为单位
     * @return true 持有锁，
     */
    public boolean lock(String key, String value, int expireTime) {
        try {
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then " +
                    "if redis.call('get',KEYS[1])==ARGV[1]" +
                    "then return redis.call('expire',KEYS[1],ARGV[2])" +
                    "else return 0 end " +
                    "end";
            RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
            Object scRet = redisTemplate.execute(redisScript, Arrays.asList(key), value, expireTime);
            return SUCCESS.equals(scRet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 使用默认的参数上锁
     * @return
     */
    public boolean lock(){
        synchronized (DistributedLock.class){
            boolean isLocked = this.lock(KEY_DEFAULT,VALUE_DEFAULT,TIME_DEFAULT);
            while(!isLocked){
                log.debug("{}","等待获取分布式锁");
                isLocked = this.lock();
            }
        }
        return this.lock(KEY_DEFAULT,VALUE_DEFAULT,TIME_DEFAULT);
    }

    /**
     * 释放锁
     * @param key
     * @param value
     * @return
     */
    public boolean release(String key,String value){
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] " +
                "then return redis.call('del',KEYS[1])" +
                "else return 0 end";
        RedisScript<String> redisScript = new DefaultRedisScript<>(script,String.class);

        Object scRet = redisTemplate.execute(redisScript, Arrays.asList(key), value);
        return SUCCESS.equals(scRet);
    }

    /**
     * 获取默认参数的锁
     * @return
     */
    public boolean release(){
        return release(KEY_DEFAULT,VALUE_DEFAULT);
    }
}
