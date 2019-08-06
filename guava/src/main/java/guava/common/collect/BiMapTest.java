package guava.common.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;

@Slf4j
public class BiMapTest {

    @Test
    public void test(){
        BiMap<String,Object> cache = HashBiMap.create(10);
        DateTime value1 = new DateTime();
        String format = "yyyy-MM-dd HH:mm:ss";
        String value2 = value1.toString(format);
        cache.put("k1",value1);
        cache.put("k2",value2);

        //①要求数据强制一致性
//        cache.put("k1",value2); //可以放入相同的键，但是值相同时，将抛出 IllegalArgumentException
        log.info("{}",cache);
//        cache.forcePut("k1",value2);
//        log.info("{}",cache);

        //②双向绑定关系
        //获取到的新map，只是改变了映射关系，键值对还是之前的、
        BiMap<Object, String> cacheI = cache.inverse();
        log.info("{}",cacheI);

        cache.put("k3",new DateTime().minusDays(1).toString(format));

        log.info("cache:{},\n\rcacheI:{}",cache,cacheI);

    }
    /**
     * 11:22:19.930 [main] INFO guava.common.collect.BiMapTest - {k1=2019-06-25T11:22:19.881+08:00, k2=2019-06-25 11:22:19}
     * 11:22:19.942 [main] INFO guava.common.collect.BiMapTest - {2019-06-25T11:22:19.881+08:00=k1, 2019-06-25 11:22:19=k2}
     * 11:22:19.944 [main] INFO guava.common.collect.BiMapTest - cache:{k1=2019-06-25T11:22:19.881+08:00, k2=2019-06-25 11:22:19, k3=2019-06-24 11:22:19},
     * cacheI:{2019-06-25T11:22:19.881+08:00=k1, 2019-06-25 11:22:19=k2, 2019-06-24 11:22:19=k3}
     */
}
