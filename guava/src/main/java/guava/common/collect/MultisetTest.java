package guava.common.collect;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.Spliterator;

@Slf4j
public class MultisetTest {

    @Test
    public void test(){
        Multiset<Integer> set = HashMultiset.create();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            set.add(random.nextInt(10));
        }
    }

}
