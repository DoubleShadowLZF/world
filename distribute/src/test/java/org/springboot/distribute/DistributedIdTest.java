package org.springboot.distribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.distribute.id.DistributedId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistributedIdTest {
    @Autowired
    private DistributedId distributedId;

    @Test
    public void idTest(){
        long id = distributedId.nextId();
        System.out.println(id);
    }
}
