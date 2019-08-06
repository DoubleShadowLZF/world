package guava.common.collect;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

@Slf4j
public class MultiMapTest {

    @Test
    public void Test(){
        Multimap map = ArrayListMultimap.create();
        map.put(1,3);
        map.put(2,3);
        map.put(3,4);
        map.put(1,4);
        log.debug("{}",map);

        Map map1 = map.asMap();
        log.debug("{}",map1);

        boolean b = map.containsValue(4);
        log.debug("{}",b);
    }

    //15:39:32.268 [main] DEBUG guava.common.collect.MultiMapTest - {1=[3, 4], 2=[3], 3=[4]}
    //15:39:32.273 [main] DEBUG guava.common.collect.MultiMapTest - {1=[3, 4], 2=[3], 3=[4]}
    //15:39:32.277 [main] DEBUG guava.common.collect.MultiMapTest - true
}
