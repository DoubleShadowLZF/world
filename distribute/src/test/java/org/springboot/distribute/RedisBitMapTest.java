package org.springboot.distribute;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.distribute.bitmap.RedisBitMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisBitMapTest {
    @Autowired
    private RedisBitMap bitMap;

    Long userId = 19950701L;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test(){
//        boolean signIs = bitMap.isSignIn(userId);
//        Assert.isTrue(!signIs);
        bitMap.signIn(userId);
        boolean signIs = bitMap.isSignIn(userId);
        Assert.isTrue(signIs);

        Long countsActive = bitMap.countsActive(userId);
        log.info("countActive:{}",countsActive);
    }

    @Test
    public void signInTest(){
//        bitMap.signIn(userId);
    }
}
