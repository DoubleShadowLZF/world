package org.springboot.distribute;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.distribute.queue.RedisQueue;
import org.springboot.distribute.queue.RedisQueueBak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisQueueTest {

    @Autowired
    private RedisQueueBak redisQueueBak;

    private String key = "redis";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisQueue redisQueue;

    @Test
    public void test01() throws InterruptedException {
        redisQueue.pushFromHead("13451235");
        Object o = redisQueue.takeFromHead();
        log.info("RedisQueue:{}",o);
    }

    @Test
    public void test02() throws InterruptedException {
        redisQueueBak.pushFromHead("13451235");
        Object o = redisQueueBak.takeFromHead();
        log.info("RedisQueue:{}",o);
    }
}
