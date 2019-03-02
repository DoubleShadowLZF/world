package org.world.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.IdGenerator;

import java.util.*;

@Slf4j
public class QueueTest {
    @Test
    public void queueTest(){
        List list = Arrays.asList(1,4,2,5,2,5,6,7,9,8);
        log.debug("现有的集合：{}",list);
        Queue q= new PriorityQueue(list);
        int index = 0;
        while(!q.isEmpty()){
            Object poll = q.poll();
            log.debug("取出第{}位的元素：{}，集合长度：{}",++index,poll,q.size());
            q.add(poll);
        }
    }

    @Test
    public void linkedListTest(){
        List list = Arrays.asList(1,4,2,5,2,5,6,7,9,8,null);
        LinkedList l = new LinkedList(list);
        l.add(4,4);
        log.debug("当前队列：{}",l);
        Object pop = l.pop();
        log.debug("栈顶元素：",pop);
        log.debug("当前队列：{}",l);
        l.push(pop == null ? 10 : pop);
        log.debug("压栈后队列：{}",l);
        Object peek = l.peek();
        log.debug("第一位元素：{}",peek);
        log.debug("当前队列：{}",l);
        Object poll = l.poll();
        log.debug("第一位元素：{}",poll);
        log.debug("当前队列：{}",l);
    }
}
